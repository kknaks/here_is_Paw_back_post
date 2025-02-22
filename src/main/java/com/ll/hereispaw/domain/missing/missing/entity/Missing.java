package com.ll.hereispaw.domain.missing.missing.entity;

import com.ll.hereispaw.domain.missing.Auhtor.entity.Author;
import com.ll.hereispaw.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class Missing extends BaseEntity {
    private String title;
    private String content;

    @ManyToOne
    private Author author;
}
