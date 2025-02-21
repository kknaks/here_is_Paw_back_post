package com.ll.hereispaw.domain.chat.chatMessage.entity;

import com.ll.hereispaw.domain.chat.chatRoom.entity.ChatRoom;
import com.ll.hereispaw.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ChatMessage extends BaseEntity {

    @ManyToOne
    private ChatRoom chatRoom;

    private String content;
}
