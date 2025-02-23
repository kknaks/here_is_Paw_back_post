package com.ll.hereispaw.domain.member.member.controller;

import com.ll.hereispaw.domain.member.member.dto.MemberInfoDto;
import com.ll.hereispaw.domain.member.member.entity.Member;
import com.ll.hereispaw.domain.member.member.service.MemberService;
import com.ll.hereispaw.global.webMvc.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {

    private final MemberService memberService;

    // 유저 기본 정보 가져오기
    @GetMapping("/me")
    public ResponseEntity<?> me(@LoginUser Member loginUser) {

        if (loginUser == null) {
            // 미로그인시 401 Unauthorized 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인을 하지 않은 유저");
        }

        MemberInfoDto userInfo = memberService.me(loginUser);

        return ResponseEntity.status(HttpStatus.OK).body(userInfo);
    }

    //어드민 - 유저 추가
    @PostMapping("/signup")
    public void signup() {

    }

    // 어드민 - 로그인
    @PostMapping
    public void login() {

    }

    // 수정?

}
