package com.ll.hereispaw.domain.noti.dto.kafka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class ImageMatchResponseDto {
  @JsonProperty("request_id")
  private String requestId;

  private String status;

  @JsonProperty("saved_type")
  private String savedType;

  @JsonProperty("compared_type")
  private String comparedType;

  private List<ImageMatchDto> matches;
}