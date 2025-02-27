package com.ll.hereispaw.domain.missing.missing.dto.response;

import com.ll.hereispaw.domain.missing.Auhtor.entity.Author;
import com.ll.hereispaw.domain.missing.missing.entity.Missing;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
public class MissingDTO {
    /**
     * 이름, 견종, 유기견 이미지, 지역, 좌표
     * 색상, 동물 등록 번호, 성별, 중성화 유무, 나이, 실종 날짜, 기타(특징), 사례금
     */
    // 필수값
    private String name;
    private String breed;
    private String geo;
    private String location;
    private String pathUrl;
    private String nickname;

    private String color;
    private String serialNumber;
    private boolean gender;
    private boolean neutered;
    private int age;
    private LocalDateTime lostDate;
    private String etc;
    private int reward;
    private int missingState;

    public MissingDTO(Missing missing) {
        nickname = missing.getAuthor().getNickname();
        pathUrl = missing.getPathUrl();

        name = missing.getName();
        breed = missing.getBreed();
        geo = missing.getGeo();
        location = missing.getLocation();
        color = missing.getColor();
        serialNumber = missing.getSerialNumber();
        gender = missing.isGender();
        neutered = missing.isNeutered();
        age = missing.getAge();
        lostDate = missing.getLostDate();
        etc = missing.getEtc();
        reward = missing.getReward();
        missingState = missing.getMissingState();
    }
}
