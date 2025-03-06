package com.ll.hereispaw.domain_msa.find.find.entity;

import com.ll.hereispaw.global_msa.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Photo extends BaseEntity {

    private String path_url; // 이미지 경로

    private Long postId; // 게시글 id
    private Long member_id; // 작성자 id

    public Photo() {

    }
}
