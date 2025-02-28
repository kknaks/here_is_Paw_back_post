package com.ll.hereispaw.domain.member.member.service;

import com.ll.hereispaw.domain.member.member.dto.request.LoginRequest;
import com.ll.hereispaw.domain.member.member.dto.request.SignupRequest;
import com.ll.hereispaw.domain.member.member.dto.request.ModifyRequest;
import com.ll.hereispaw.domain.member.member.dto.response.LoginResponse;
import com.ll.hereispaw.domain.member.member.dto.response.MemberInfoDto;
import com.ll.hereispaw.domain.member.member.entity.Member;
import com.ll.hereispaw.domain.member.member.repository.MemberRepository;
import com.ll.hereispaw.global.error.ErrorCode;
import com.ll.hereispaw.global.exception.CustomException;
import com.ll.hereispaw.global.rq.Rq;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    @Value("${custom.bucket.name}")
    private String bucketName;

    @Value("${custom.bucket.region}")
    private String region;

    @Value("${custom.bucket.avatar}")
    private String dirName;

    private final S3Client s3Client;

    private final AuthTokenService authTokenService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final Rq rq;

    private final String defaultAvatar = "https://paw-bucket-1.s3.ap-northeast-2.amazonaws.com/profile-img/defaultAvatar.jpg";

    public long count() {
        return memberRepository.count();
    }

    public MemberInfoDto me(Member loginUser) {
        log.debug("loginUser : {}", loginUser.getUsername());

        return new MemberInfoDto(loginUser);
    }

    @Transactional
    public Member signup(String username, String password, String nickname, String avatar) {
        memberRepository
                .findByUsername(username)
                .ifPresent(user -> {
                    throw new ServiceException("해당 username은 이미 사용중입니다.");
                });

        Member member = Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .avatar(avatar)
                .apiKey(UUID.randomUUID().toString())
                .build();

        return memberRepository.save(member);
    }

    @Transactional
    public Member signup(SignupRequest signupRq) {
        String username = signupRq.username();

        memberRepository
                .findByUsername(username)
                .ifPresent(user -> {
                    throw new ServiceException("해당 username은 이미 사용중입니다.");
                });

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(signupRq.password()))
                .nickname(signupRq.nickname())
                .apiKey(UUID.randomUUID().toString())
                .avatar(defaultAvatar)
                .build();

        return memberRepository.save(member);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Optional<Member> findById(long authorId) {
        return memberRepository.findById(authorId);
    }

    public Optional<Member> findByApiKey(String apiKey) {
        return memberRepository.findByApiKey(apiKey);
    }

    public String genAccessToken(Member member) {
        return authTokenService.genAccessToken(member);
    }

    public String genAuthToken(Member member) {
        return member.getApiKey() + " " + genAccessToken(member);
    }

    public Member getMemberFromAccessToken(String accessToken) {
        Map<String, Object> payload = authTokenService.payload(accessToken);

        if (payload == null) return null;

        long id = (long) payload.get("id");
        String username = (String) payload.get("username");
        String nickname = (String) payload.get("nickname");

        Member member = new Member(id, username, nickname);

        return member;
    }

    @Transactional
    public void modify(Member member, @NotBlank String nickname, String avatar) {
        member.setNickname(nickname);
        member.setAvatar(avatar);
    }

    @Transactional
    public MemberInfoDto modify(Member loginUser, ModifyRequest modifyRequest) {
        Member member = memberRepository.findByUsername(modifyRequest.username()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (!loginUser.getUsername().equals(member.getUsername())) {
            throw new CustomException(ErrorCode.SC_FORBIDDEN);
        }

        if (modifyRequest.hasNickname()) {
            member.setNickname(modifyRequest.nickname());
        }

        if (modifyRequest.hasProfile()) {
            String avatar = member.getAvatar();


            // S3 삭제
            if (avatar != null && !avatar.equals(defaultAvatar)) {
                deleteImageToS3(member.getAvatar());
            }

            String fileName = uploadImageToS3(modifyRequest.profileImage());
            member.setAvatar(fileName);
        }

        memberRepository.save(member);

        return new MemberInfoDto(member);
    }

    @Transactional
    public Member modifyOrJoin(String username, String nickname, String avatar) {
        Optional<Member> opMember = findByUsername(username);
        if (opMember.isPresent()) {
            Member member = opMember.get();
            modify(member, nickname, avatar);
            return member;
        }
        return signup(username, "", nickname, avatar);
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRq) {
        Member member = memberRepository.findByUsername(loginRq.username())
                .orElseThrow(() -> new EntityNotFoundException("해당 유저는 없습니다."));

        if (passwordEncoder.matches(member.getPassword(), loginRq.password()))
            throw new ServiceException("비밀번호가 일치하지 않습니다.");

        String token = rq.makeAuthCookies(member);

        return new LoginResponse(new MemberInfoDto(member), member.getApiKey(), token);
    }

    @Transactional
    public void radius_update(Member loginUser, Integer radius) {
        Member user = memberRepository.findById(loginUser.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        user.setRadius(radius);

        memberRepository.save(user);
    }

    private String uploadImageToS3(MultipartFile file) {
        try {
            String filename = getUuidFilename(file);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(dirName + "/" + filename)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return getS3FileUrl(filename);
        }catch (IOException e) {
            throw new CustomException(ErrorCode.S3_UPLOAD_ERROR);
        }
    }

    private String getUuidFilename(MultipartFile file) {
        // ContentType으로부터 확장자 추출
        String contentType = file.getContentType();
        String extension = switch (contentType) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            case "image/gif" -> "gif";
            default -> "jpg";  // 기본값 설정
        };

        // UUID 파일명 생성
        return UUID.randomUUID().toString() + "." + extension;
    }

    private String getS3FileUrl(String fileName) {
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + dirName + "/" + fileName;
    }

    private void deleteImageToS3(String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(dirName + "/" + fileName)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }
}