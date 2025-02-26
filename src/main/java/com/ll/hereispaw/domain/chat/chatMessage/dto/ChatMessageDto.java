package com.ll.hereispaw.domain.chat.chatMessage.dto;

import com.ll.hereispaw.domain.chat.chatMessage.entity.ChatMessage;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMessageDto {

    private Long chatMessageId;
    private Long chatRoomId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long memberId;
    private String memberNickname;


    private String content;

    public ChatMessageDto(ChatMessage chatMessage){

        this.chatMessageId      = chatMessage.getId();
        this.chatRoomId         = chatMessage.getChatRoom().getId();
        this.content            = chatMessage.getContent();
        this.createdDate        = chatMessage.getCreateDate();
        this.modifiedDate       = chatMessage.getModifyDate();

        /*Member member = chatMessage.getMember();

        this.memberId = member.getId();
        this.memberNickname = member.getNickname();*/

        this.memberId = chatMessage.getMember().getId();
        this.memberNickname = chatMessage.getMember().getNickname();


    }
}
