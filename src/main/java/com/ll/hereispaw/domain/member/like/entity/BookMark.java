package com.ll.hereispaw.domain.member.like.entity;

import com.ll.hereispaw.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
//@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "id", "type"}))
public class BookMark extends BaseEntity {
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @Column(name = "id")
//    private Long postId;
//
//    @Column(name = "type")
//    private Boolean type; // 0: 신고, 1: 좋아요
//
//    // 게시글 타입을 구분하기 위한 추가 필드
//    @Transient // DB에 저장되지 않는 필드
//    private String postType; // "MISSING" 또는 "FIND"
}
