package com.ll.hereispaw.domain.chat.chatRoom.service;

import com.ll.hereispaw.domain.chat.chatMessage.entity.ChatMessage;
import com.ll.hereispaw.domain.chat.chatRoom.entity.ChatRoom;
import com.ll.hereispaw.domain.chat.chatRoom.repository.ChatRoomRepository;
import com.ll.hereispaw.domain.member.member.entity.Member;
import com.ll.hereispaw.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    //private final MemberService memberService; 의존을 서비스에서 했는데 findById가 되는지 궁금
    private final MemberRepository memberRepository;

    //채팅방 생성
    public ChatRoom createRoom(Member chatUser,Long targetUserId){
        Member targetUser = memberRepository.findById(targetUserId).orElse(null);
        if (targetUser == null) {
            throw new RuntimeException("상대방 사용자를 찾을 수 없습니다.");
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .chatUser(chatUser)
                .targetUser(targetUser)
                .createDate(LocalDateTime.now())
                //.roomState()
                .build();

        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    //사용자간의 채팅방 조회 후 생성
    public ChatRoom findOrCreateRoom(Member chatUser,Long targetUserId){
        //상대방 조회 람다 연습
        Member targetUser = memberRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("상대방 사용자를 찾을 수 없습니다."));
        //존재하는 채팅방 조회
        Optional<ChatRoom> existingRoom = chatRoomRepository.findByRoom(chatUser, targetUser, targetUser, chatUser);
        //없으면 채팅방 생성
        if(existingRoom.isPresent()){
            return existingRoom.get();
        }else {
            return createRoom(chatUser, targetUserId);
        }
    }

    //채팅방 목록 조회
    public List<ChatRoom> roomList(Member member){
        return chatRoomRepository.findByRoomList(member, member);
    }
    /*//채팅방 목록
    public Page<ChatRoom> roomList(int page){
        //Sort sort = Sort.by(Sort.Order.desc("새로운 채팅 순서 메세지아이디???"));
        Pageable pageable = PageRequest.of(page,10);
    return chatRoomRepository.findAll(pageable);
    }*/
    /*//채팅방 목록
    public List<ChatRoom> roomList(){
        return chatRoomRepository.findAll();
    }*/

/*
    //채팅방
    public ChatRoom viewRoom(Long id){
        Optional<ChatRoom> chatRoom = this.chatRoomRepository.findById(id);
        if(chatRoom.isPresent()){
            return chatRoom.get();
        }else {
            throw new RuntimeException("error ChatService viewRoom");
        }
    }
*/
    //채팅방 입장
    public ChatRoom viewRoom(Long id, Long messageId) {
        Optional<ChatRoom> chatRoom = this.chatRoomRepository.findById(id);
        if (chatRoom.isPresent()) {
            ChatRoom cR = chatRoom.get();
            // 자동으로 로딩된 chatMessages 리스트 자동 로딩 되지만 명시적으로 참조해줘야 후속 작업에서 사용할 수 있음
            List<ChatMessage> messages = cR.getChatMessages();
            int index = -1;
            for (int i = 0; i < messages.size(); i++) {
                if (messages.get(i).getId() == messageId) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                messages = messages.subList(index + 1, messages.size());
            }
            return cR;
        } else {
            throw new RuntimeException("error ChatService viewRoom");
        }
    }


}
