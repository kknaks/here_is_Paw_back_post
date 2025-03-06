package com.ll.hereispaw.domain_msa.chat.chatRoom.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ll.hereispaw.domain_msa.chat.chatMessage.entity.ChatMessage;
import com.ll.hereispaw.domain_msa.member.member.entity.Member;
import com.ll.hereispaw.global_msa.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ChatRoom extends BaseEntity {

    private int roomState;

    @OneToMany(fetch = FetchType.EAGER ,mappedBy = "chatRoom" )
    @JsonManagedReference
    private List<ChatMessage> chatMessages;

    @JsonBackReference
    @ManyToOne
    private Member chatUser;

    @JsonBackReference
    @ManyToOne
    private Member targetUser;
}