package com.ll.hereispaw.domain.member.member.controller;

import com.ll.hereispaw.domain.member.member.dto.MemberInfoDto;
import com.ll.hereispaw.domain.member.member.entity.Member;
import com.ll.hereispaw.global.webMvc.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {

    @GetMapping("/auth")
    public MemberInfoDto auth(@LoginUser Member member) {
        return new MemberInfoDto(member);
    }

}
