package com.ll.hereispaw.domain.member.bookmark.entity;

import com.ll.hereispaw.domain.member.member.entity.Member;
import com.ll.hereispaw.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
//@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "post_id", "type"}))
public class Bookmark extends BaseEntity {
    public enum BookmarkType {
        MISSING, FIND
    }

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "type", nullable = false)
    private BookmarkType type; // 0: missing, 1: find

}
