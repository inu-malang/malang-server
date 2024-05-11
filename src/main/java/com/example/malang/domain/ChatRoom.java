package com.example.malang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatParticipation> chatParticipationList = new ArrayList<>();

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "request_id")
    private Request request;

    public void setRequest(Request request) {
        this.request = request;
        request.setChatRoom(this);
    }

    public static ChatRoom createChatRoom(Request request) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.name = request.getPost().getTitle();
        chatRoom.setRequest(request);
        return chatRoom;
    }
}
