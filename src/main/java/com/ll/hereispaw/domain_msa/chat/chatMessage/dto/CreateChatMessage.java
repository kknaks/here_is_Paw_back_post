package com.ll.hereispaw.domain_msa.chat.chatMessage.dto;

import lombok.Data;

@Data
public class CreateChatMessage {
    private Long chatMessageId;
    private String content;
    private Long memberId;
    private String memberNickname;
}