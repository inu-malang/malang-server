package com.example.malang.domain;

import com.example.malang.dto.ChatRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    private String content;

    private String sender;

    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_participation_id")
    private ChatParticipation chatParticipation;

    public void setChatParticipation(ChatParticipation chatParticipation) {
        this.chatParticipation = chatParticipation;
        chatParticipation.getChatList().add(this);
    }


    public static Chat createChat(ChatParticipation chatParticipation, ChatRequest chatRequest) {
        Chat chat = new Chat();
        chat.setChatParticipation(chatParticipation);
        chat.content = chatRequest.getContent();
        chat.sender = chatRequest.getSender();
        chat.timestamp = chatRequest.getTimestamp();

        return chat;
    }
}
