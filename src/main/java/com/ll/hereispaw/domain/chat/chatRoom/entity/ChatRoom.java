package com.ll.hereispaw.domain.chat.chatRoom.entity;

import com.ll.hereispaw.domain.chat.chatMessage.entity.ChatMessage;
import com.ll.hereispaw.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ChatRoom extends BaseEntity {

    private int roomState;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessages;
}
