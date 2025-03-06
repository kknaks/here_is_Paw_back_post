package com.ll.hereispaw.domain_msa.chat.chatMessage.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ll.hereispaw.domain_msa.chat.chatRoom.entity.ChatRoom;
import com.ll.hereispaw.domain_msa.member.member.entity.Member;
import com.ll.hereispaw.global_msa.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ChatMessage extends BaseEntity {

    @JsonBackReference
    @ManyToOne
    private ChatRoom chatRoom;

    @JsonBackReference
    @ManyToOne
    private Member member;

    private String content;
}
