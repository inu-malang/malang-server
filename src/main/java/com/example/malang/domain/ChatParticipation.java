package com.example.malang.domain;

import com.example.malang.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ChatParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_participation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "chatParticipation")
    private List<Chat> chatList = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
        member.getChatParticipationList().add(this);
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.getChatParticipationList().add(this);
    }

    public static ChatParticipation createChatParticipation(Member member, ChatRoom chatRoom) {
        ChatParticipation chatParticipation = new ChatParticipation();
        chatParticipation.setMember(member);
        chatParticipation.setChatRoom(chatRoom);

        return chatParticipation;
    }
}
