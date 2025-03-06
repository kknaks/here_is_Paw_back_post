package com.ll.hereispaw.domain_msa.find.find.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindRequest {
    private Long id;

    private String title; // 제목
    private String situation; // 발견 상황
    private String breed; // 견종
    private String location; // 지역
    private String name; // 이름
    private String color; // 색상
    private String etc; // 기타 특징


//    private Point geo; // 좌표인데 타입 확인 필요
    private double x;
    private double y;

    private int gender; // 성별
    private int serial_number; // 등록 번호
    private int age; // 나이
    private int neutered; // 중성화 유무


    private LocalDateTime find_date; // 발견 시간

    private Long shelter_id; // 보호소 id

    // 기존 FindPhotoRequest 필드
    private String path_url; // 이미지 경로
}
