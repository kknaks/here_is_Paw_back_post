package com.ll.hereispaw.domain.missing.missing.controller;

import com.ll.hereispaw.domain.missing.missing.dto.request.MissingRequestDTO;
import com.ll.hereispaw.domain.missing.missing.dto.response.MissingDTO;
import com.ll.hereispaw.domain.missing.missing.service.MissingService;
import com.ll.hereispaw.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missings")
public class ApiV1MissingController {
    private final MissingService missingService;

    // 전체 조회
    @GetMapping
    public RsData<List<MissingDTO>> lists() {
        return RsData.of("전체 조회 성공", missingService.list());
    }

    // 작성
    @PostMapping("/write")
    public RsData<MissingDTO> write(@Valid @RequestBody MissingRequestDTO missingRequestDto) {
        return RsData.of("신고글 작성 성공", missingService.write(missingRequestDto));
    }

    // 단건 조회
    @GetMapping("/{missingId}")
    public RsData<MissingDTO> detail(@PathVariable("missingId") Long missingId) {
        return RsData.of("단건 조회 성공", missingService.findById(missingId));
    }

    // 수정
    @PatchMapping("/{missingId}")
    public RsData<MissingDTO> update(@Valid @RequestBody MissingRequestDTO missingRequestDto, @PathVariable("missingId") Long missingId) {
        return RsData.of("신고글 수정 성공", missingService.update(missingRequestDto, missingId));
    }

    // 삭제
    @DeleteMapping("/{missingId}")
    public String delete(@PathVariable Long missingId) {
        missingService.delete(missingId);

        return "신고글 삭제했다.";
    }
}
