package com.ll.hereispaw.domain_msa.missing.missing.dto.response;

import com.ll.hereispaw.domain_msa.missing.missing.entity.Missing;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class MissingResponse {
    /**
     * 이름, 견종, 유기견 이미지, 지역, 좌표
     * 색상, 동물 등록 번호, 성별, 중성화 유무, 나이, 실종 날짜, 기타(특징), 사례금
     */
    private Long id;

    // 필수값
    private String name;
    private String breed;
    private String geo;
    private String location;
    private String pathUrl;
    private Long authorId;
    private String nickname;

    private String color;
    private String serialNumber;
    private Integer gender;
    private Integer neutered;
    private Integer age;
    private LocalDateTime lostDate;
    private String etc;
    private Integer reward;
    private Integer missingState;

    public MissingResponse(Missing missing) {
        id = missing.getId();
        authorId = missing.getAuthorId();
        nickname = missing.getNickname();
        pathUrl = missing.getPathUrl();

        name = missing.getName();
        breed = missing.getBreed();
        geo = missing.getGeo().toString();
        location = missing.getLocation();
        color = missing.getColor();
        serialNumber = missing.getSerialNumber();
        gender = missing.getGender();
        neutered = missing.getNeutered();
        age = missing.getAge();
        lostDate = missing.getLostDate();
        etc = missing.getEtc();
        reward = missing.getReward();
        missingState = missing.getMissingState();
    }
}
