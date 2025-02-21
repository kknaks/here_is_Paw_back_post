package com.ll.hereispaw.domain.chat.chatMessage.controller;

import com.ll.hereispaw.domain.chat.chatMessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class ApiV1ChatMessageController {

    private final ChatMessageService chatMessageService;
}
