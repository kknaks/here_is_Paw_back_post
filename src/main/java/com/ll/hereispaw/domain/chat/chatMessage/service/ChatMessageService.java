package com.ll.hereispaw.domain.chat.chatMessage.service;

import com.ll.hereispaw.domain.chat.chatMessage.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    public final ChatMessageRepository chatMessageRepository;
}
