package com.ll.hereispaw.domain.member.profile.entity;

import com.ll.hereispaw.domain.member.member.entity.Member;
import com.ll.hereispaw.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends BaseEntity {

  @OneToOne
  private Member member;

  private String profileImageUrl;

  private String profileImgOriginName;
}
