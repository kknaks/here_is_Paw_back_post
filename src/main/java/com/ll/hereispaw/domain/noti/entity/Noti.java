package com.ll.hereispaw.domain.noti.entity;

import com.ll.hereispaw.global.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Noti extends BaseEntity {

  private String userId;

  private String eventName;

  @ManyToOne(cascade = CascadeType.ALL)
  private NotiRequest notiRequest;

  private boolean read;

  public void markAsRead() {
    this.read = true;
  }


}
