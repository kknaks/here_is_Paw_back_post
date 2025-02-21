package com.ll.hereispaw.domain.chat.chatRoom.controller;


import com.ll.hereispaw.domain.chat.chatRoom.entity.ChatRoom;
import com.ll.hereispaw.domain.chat.chatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("Api/V1/chatRoom")
public class ApiV1ChatRoomController {

    private final ChatRoomService chatRoomService;

    //채팅방 목록
    @GetMapping("")
    public Page<ChatRoom> roomList(@RequestParam(value = "page", defaultValue = "0")int page){
        Page<ChatRoom> paging = this.chatRoomService.roomList(page);
        return paging;
    }

    //채팅방
    @GetMapping("/{id}")
    public ChatRoom viewRoom(@PathVariable("chatRoomId")Long chatRoomId){
        ChatRoom chatRoom =this.chatRoomService.viewRoom(chatRoomId);
        return chatRoom;
    }
}
