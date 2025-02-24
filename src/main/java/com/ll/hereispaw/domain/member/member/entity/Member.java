package com.ll.hereispaw.domain.member.member.entity;

import com.ll.hereispaw.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class Member extends BaseEntity {
    private String username;
    private String password;
    private String nickname;
}

