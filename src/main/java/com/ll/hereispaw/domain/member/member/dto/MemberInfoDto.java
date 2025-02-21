package com.ll.hereispaw.domain.member.member.dto;


import com.ll.hereispaw.domain.member.member.entity.Member;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class MemberInfoDto {
    private final long id;

    @NonNull
    private final String nickname;

    public MemberInfoDto(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
    }
}