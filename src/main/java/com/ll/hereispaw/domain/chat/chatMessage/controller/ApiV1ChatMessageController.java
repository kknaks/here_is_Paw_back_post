package com.ll.hereispaw.domain.chat.chatMessage.controller;

import com.ll.hereispaw.domain.chat.chatMessage.dto.ChatMessageDto;
import com.ll.hereispaw.domain.chat.chatMessage.dto.CreateChatMessage;
import com.ll.hereispaw.domain.chat.chatMessage.entity.ChatMessage;
import com.ll.hereispaw.domain.chat.chatMessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat/{chatRoom-id}/messages")
public class ApiV1ChatMessageController {

    private final ChatMessageService chatMessageService;

    //메세지 생성
    @PostMapping("")
    public ChatMessageDto writeMessage(@PathVariable("chatRoom-id") Long chatRoomId, @RequestBody CreateChatMessage createChatMessage){
        ChatMessage chatMessage = this.chatMessageService.writeMessage(chatRoomId, createChatMessage.getContent());
        ChatMessageDto messages = new ChatMessageDto(chatMessage);
        return messages;
    }
}
