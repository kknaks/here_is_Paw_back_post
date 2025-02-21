package com.ll.hereispaw.domain.chat.chatRoom.dto;

import com.ll.hereispaw.domain.chat.chatRoom.entity.ChatRoom;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatRoomDto {

    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int roomState;

    public ChatRoomDto(ChatRoom chatRoom){
        this.id = chatRoom.getId();
        this.createdDate = chatRoom.getCreatedDate();
        this.modifiedDate = chatRoom.getModifiedDate();
        this.roomState = chatRoom.getRoomState();
    }
}
