package com.ll.hereispaw.domain_msa.chat.chatMessage.dto;

import com.ll.hereispaw.domain_msa.chat.chatMessage.entity.ChatMessage;
import com.ll.hereispaw.domain_msa.member.member.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponseDto {
    private Long memberId;
    private Long chatMessageId;
    private String memberNickname;
    private String content;
    private LocalDateTime createdDate;

    public ChatMessageResponseDto(ChatMessage chatMessage, Member loginUser) {
        this.memberId = chatMessage.getMember().getId();
        this.chatMessageId = chatMessage.getId();
        this.memberNickname = chatMessage.getMember().equals(loginUser) ? "me" : chatMessage.getMember().getNickname();
        this.content = chatMessage.getContent();
        this.createdDate = chatMessage.getCreateDate();
    }
}
