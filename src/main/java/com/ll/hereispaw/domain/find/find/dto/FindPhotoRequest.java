package com.ll.hereispaw.domain.find.find.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPhotoRequest {
    private Long id;

    private String path_url; // 이미지 경로

    private Long find_post_id; // 게시글 id
    private Long member_id; // 작성자 id
}
