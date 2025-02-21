package com.ll.hereispaw.domain.chat.chatRoom.entity;

import com.ll.hereispaw.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChatRoom extends BaseEntity {

    private int roomState;

}
