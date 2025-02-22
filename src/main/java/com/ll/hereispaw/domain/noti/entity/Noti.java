package com.ll.hereispaw.domain.noti.entity;

import com.ll.hereispaw.global.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Noti extends BaseEntity {
  String title;
  String content;
}
