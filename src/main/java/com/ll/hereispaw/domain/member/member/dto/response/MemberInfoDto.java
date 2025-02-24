package com.ll.hereispaw.domain.member.member.dto.response;


import com.ll.hereispaw.domain.member.member.entity.Member;
import jakarta.validation.constraints.NotNull;

public record MemberInfoDto(long id, @NotNull String nickname) {
    public MemberInfoDto(Member member) {
        this(member.getId(), member.getNickname());
    }
}