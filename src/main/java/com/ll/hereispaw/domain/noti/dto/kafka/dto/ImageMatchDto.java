package com.ll.hereispaw.domain.noti.dto.kafka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// 내부 matches 배열을 위한 DTO 클래스
@Data
public class ImageMatchDto {

  // private Long senderId;

  // private Long receiverId;

  @JsonProperty("image_id")
  private Long imageId;

  @JsonProperty("post_type")
  private String postType;


  @JsonProperty("post_id")
  private Long postId;

  private Double similarity;


}