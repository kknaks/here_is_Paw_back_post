package com.ll.hereispaw.domain.find.find.service;

import com.ll.hereispaw.domain.find.find.dto.DogFaceRequestDto;
import com.ll.hereispaw.domain.find.find.dto.FindDto;
import com.ll.hereispaw.domain.find.find.entity.FindPost;
import com.ll.hereispaw.domain.find.find.entity.Photo;
import com.ll.hereispaw.domain.find.find.repository.FindPhotoRepository;
import com.ll.hereispaw.domain.find.find.repository.FindRepository;
import com.ll.hereispaw.domain.missing.missing.entity.Missing;
import com.ll.hereispaw.global.error.ErrorCode;
import com.ll.hereispaw.global.globalDto.GlobalResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.processing.Find;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FindService {
    private static String POST_TYPE = "finding";

    private final FindRepository findRepository;
    private final FindPhotoRepository findPhotoRepository;

    @Value("${custom.bucket.name}")
    private String bucketName;

    @Value("${custom.bucket.region}")
    private String region;

    @Value("${custom.bucket.finding}")
    private String dirName;

    private final S3Client s3Client;

    //카프카 발행자 템플릿
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public Long saveFind(
            String title,
            String situation,
            String breed,
            Point geo,
            String location,
            String name,
            String color,
            String etc,
            int gender,
            int age,
            int state,
            int neutered,
            LocalDateTime find_date,
            Long member_id,
            Long shelter_id,
            MultipartFile file
            ) {
        FindPost findPost = new FindPost();

        findPost.setTitle(title);
        findPost.setSituation(situation);
        findPost.setBreed(breed);
        findPost.setGeo(geo);
        findPost.setLocation(location);
        findPost.setName(name);
        findPost.setColor(color);
        findPost.setEtc(etc);
        findPost.setGender(gender);
        findPost.setAge(age);
        findPost.setState(state);
        findPost.setNeutered(neutered);
        findPost.setFind_date(find_date);
        findPost.setMember_id(member_id);
        findPost.setShelter_id(shelter_id);

        FindPost savedPost = findRepository.save(findPost);

        String file_path = s3Upload(savedPost, file);

        System.out.println("response check: " + file_path);

        //카프카 메시지 발행
        DogFaceRequestDto dogFaceRequestDto = DogFaceRequestDto.builder()
            .type("save")
            .image(file_path)
            .postType(POST_TYPE)
            .postId(savedPost.getId())
            .postMemberId(savedPost.getMember_id())
            .build();
        kafkaTemplate.send("dog-face-request", dogFaceRequestDto);

        return savedPost.getId(); // 저장된 find_post_id 반환
    }

    public List<FindDto> findAll() {
        List<FindDto> findDtos = new ArrayList<>();
        findRepository.findAll().forEach(e -> {
            // find_post_id를 이용해 첫 번째 이미지 URL 가져오기

            findDtos.add(
                    FindDto.builder()
                            .id(e.getId())
                            .breed(e.getBreed())
                            .geo(e.getGeo())
                            .location(e.getLocation())
                            .name(e.getName())
                            .color(e.getColor())
                            .gender(e.getGender())
                            .etc(e.getEtc())
                            .age(e.getAge())
                            .neutered(e.getNeutered())
                            .find_date(e.getFind_date())
                            .member_id(e.getMember_id())
                            .shelter_id(e.getShelter_id())
                            .path_url(e.getPath_url()) // 이미지 URL 추가
                            .build()
            );
        });

        return findDtos;
    }

    public FindDto findById(Long postId) {

        FindPost findPost = findRepository.findById(postId).get();

        FindDto findDto = FindDto.builder()
                .id(findPost.getId())
                .title(findPost.getTitle())
                .situation(findPost.getSituation())
                .age(findPost.getAge())
                .breed(findPost.getBreed())
                .color(findPost.getColor())
                .etc(findPost.getEtc())
                .find_date(findPost.getFind_date())
                .gender(findPost.getGender())
                .geo(findPost.getGeo())
                .location(findPost.getLocation())
                .member_id(findPost.getMember_id())
                .name(findPost.getName())
                .neutered(findPost.getNeutered())
                .path_url(findPost.getPath_url())
                .member_id(findPost.getMember_id())
                .build();

        return findDto;
    }

    public String updateFind(
            Long postId,
            String title,
            String situation,
            String breed,
            Point geo,
            String location,
            String name,
            String color,
            String etc,
            int gender,
            int age,
            int state,
            int neutered,
            LocalDateTime find_date,
            Long member_id,
            Long shelter_id
    ) {
        FindPost findPost = findRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다."));

        findPost.setTitle(title);
        findPost.setSituation(situation);
        findPost.setBreed(breed);
        findPost.setGeo(geo);
        findPost.setLocation(location);
        findPost.setName(name);
        findPost.setColor(color);
        findPost.setEtc(etc);
        findPost.setGender(gender);
        findPost.setAge(age);
        findPost.setState(state);
        findPost.setNeutered(neutered);
        findPost.setFind_date(find_date);
        findPost.setMember_id(member_id);
        findPost.setShelter_id(shelter_id);

        FindPost savedPost = findRepository.save(findPost);

        return "수정 완료";
    }

    public String updateFindPhoto(String path_url, Long member_id, Long postId) {
        Photo photo = findPhotoRepository.findByPostId(postId);

        photo.setPath_url(path_url);
        photo.setMember_id(member_id);
        photo.setPostId(postId);

        Photo savedPhoto = findPhotoRepository.save(photo);

        //카프카 메시지 발행
//        DogFaceRequestDto dogFaceRequestDto = DogFaceRequestDto.builder()
//                .type("save")
//                .image(path_url)
//                .postType(POST_TYPE)
//                .postId(postId)
//                .build();
//        kafkaTemplate.send("dog-face-request", dogFaceRequestDto);
        return "사진 수정 완료";
    }

    // s3 매서드
    public String s3Upload(
            FindPost findPost,
            MultipartFile file) {
        try {
            String filename = getUuidFilename(file);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(dirName + "/" + filename)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            findPost.setPath_url(getS3FileUrl(filename));

        } catch (IOException e) {
            return "error: "+e;
        }

        return findPost.getPath_url();
    }

    public GlobalResponse<String> s3Delete(FindPost findPost) {
        try {
            String fileName = getFileNameFromS3Url(findPost.getPath_url());
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(dirName + "/" + fileName)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
            findPost.setPath_url(getS3FileUrl("defaultAvatar.jpg"));
        } catch (Exception e) {
            log.warn("Failed to delete old profile image", e);
        }
        return GlobalResponse.success("삭제 성공");
    }

    public GlobalResponse<String> s3Update(
            FindPost findPost,
            MultipartFile file) {

        if (findPost.getFind_date() != null) {
            s3Delete(findPost);
            s3Upload(findPost, file);
        }

        return GlobalResponse.success("수정 성공");
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

    public String getS3FileUrl(String fileName) {
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + dirName + "/" + fileName;
    }

    public String getFileNameFromS3Url(String s3Url) {
        return s3Url.substring(s3Url.lastIndexOf('/') + 1);
    }

}
