package com.ll.hereispaw.domain_msa.chat.chatMessage.repository;

import com.ll.hereispaw.domain_msa.chat.chatMessage.entity.ChatMessage;
import com.ll.hereispaw.domain_msa.chat.chatRoom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    //메세지 조회
//    List<ChatMessage> findByChatRoomId(Long chatRoomId);
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);

}
