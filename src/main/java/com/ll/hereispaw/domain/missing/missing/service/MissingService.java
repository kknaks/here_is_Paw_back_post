package com.ll.hereispaw.domain.missing.missing.service;

import com.ll.hereispaw.domain.missing.Auhtor.entity.Author;
import com.ll.hereispaw.domain.missing.Auhtor.repository.AuthorRepository;
import com.ll.hereispaw.domain.missing.missing.dto.request.MissingRequestDTO;
import com.ll.hereispaw.domain.missing.missing.dto.response.MissingDTO;
import com.ll.hereispaw.domain.missing.missing.entity.Missing;
import com.ll.hereispaw.domain.missing.missing.repository.MissingRepository;
import com.ll.hereispaw.domain.member.member.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class MissingService {
    private final MissingRepository missingRepository;
    private final AuthorRepository authorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public MissingDTO write(MissingRequestDTO missingRequestDto) {

        log.debug("author : {}", missingRequestDto.getAuthor());

        Missing missing = missingRepository.save(
                Missing.builder()
                        .author(missingRequestDto.getAuthor())
                        .title(missingRequestDto.getTitle())
                        .content(missingRequestDto.getContent())
                        .build()
        );

        return new MissingDTO(missing);
    }

    public Author of(Member member) {
        return entityManager.getReference(Author.class, member.getId());
    }

    public List<MissingDTO> list() {
        List<Missing> missings = missingRepository.findAll();
        List<MissingDTO> missingDTOS = new ArrayList<>();

        for (Missing missing : missings) {

            Author author = authorRepository.findById(missing.getAuthor().getId()).orElse(null);

            missingDTOS.add(
                    MissingDTO.builder()
                        .nickname(author.getNickname())
                        .title(missing.getTitle())
                        .content(missing.getContent()).build()
            );
        }

        return missingDTOS;
    }

    public MissingDTO findById(Long missingId) {
        Missing missing = missingRepository.findById(missingId)
                .orElseThrow(() -> new RuntimeException("Missing not found with id: " + missingId));

        return new MissingDTO(missing);
    }

    public MissingDTO update(MissingRequestDTO missingRequestDTO, Long missingId) {

        Missing missing = missingRepository.findById(missingId).orElse(null);

        // 수정 시 유저 확인
        // - 코드 작성해야함.

        missing.setTitle(missingRequestDTO.getTitle());
        missing.setContent(missingRequestDTO.getContent());

        missingRepository.save(missing);

        return new MissingDTO(missing);

    }

    public String delete(Long missingId) {
        Missing missing = missingRepository.findById(missingId).orElse(null);

        missingRepository.delete(missing);

        return "신고글 삭제";
    }
}

