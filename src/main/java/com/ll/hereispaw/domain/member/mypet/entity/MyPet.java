package com.ll.hereispaw.domain.member.mypet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ll.hereispaw.domain.member.member.entity.Member;
import com.ll.hereispaw.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPet extends BaseEntity {

    @ManyToOne
    @JsonBackReference
    private Member member;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String breed;

//    @Column(nullable = false)
//    private String imageUrl;

    private String color;

    private String serialNumber;

    private int gender = 0;

    private boolean neutered;

    private int age;

    @Column(columnDefinition = "TEXT")
    private String etc;
}