package com.ll.hereispaw.domain_msa.chat.chatMessage.service;

import com.ll.hereispaw.domain_msa.chat.chatMessage.dto.ChatMessageResponseDto;
import com.ll.hereispaw.domain_msa.chat.chatMessage.entity.ChatMessage;
import com.ll.hereispaw.domain_msa.chat.chatMessage.repository.ChatMessageRepository;
import com.ll.hereispaw.domain_msa.chat.chatRoom.entity.ChatRoom;
import com.ll.hereispaw.domain_msa.chat.chatRoom.repository.ChatRoomRepository;
import com.ll.hereispaw.domain_msa.chat.chatRoom.service.ChatRoomService;
import com.ll.hereispaw.domain_msa.member.member.entity.Member;
import com.ll.hereispaw.global_msa.error.ErrorCode;
import com.ll.hereispaw.global_msa.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatRoomService chatRoomService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    //메세지 생성
    public ChatMessage writeMessage(Long id, Long messageId, String content, Member member) {
        ChatRoom chatRoom = this.chatRoomRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(content);
        chatMessage.setMember(member);
        chatMessage.setChatRoom(chatRoom);
        return this.chatMessageRepository.save(chatMessage);
    }

    //메세지 조회
    public List<ChatMessageResponseDto> listMessage(Member loginUser, Long chatRoomId) {
        ChatRoom chatRoom = this.chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        List<ChatMessageResponseDto> chatMessageResponseDtos = new ArrayList<>();

        for (ChatMessage chatMessage : chatRoom.getChatMessages()) {
            chatMessageResponseDtos.add(new ChatMessageResponseDto(chatMessage, loginUser));
        }

        return chatMessageResponseDtos;
    }

}
