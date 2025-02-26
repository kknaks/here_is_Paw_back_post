package com.ll.hereispaw.domain.member.mypet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MyPetRequest {
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "견종은 필수 입력 값입니다.")
    private String breed;

//    @Column(nullable = false)
//    private String imageUrl;

    private String color;
    private String serialNumber;
    private Integer gender;
    private boolean neutered;
    private Integer age;
    private String etc;

    public boolean hasColor() {
        return color != null;
    }

    public boolean hasSerialNumber() {
        return serialNumber != null;
    }

    public boolean hasGender() {
        return gender != null;
    }

    public boolean hasAge() {
        return age != null;
    }

    public boolean hasEtc() {
        return etc != null;
    }
}
