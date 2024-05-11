package com.example.malang.dto;

import com.example.malang.domain.Chat;
import com.example.malang.domain.ChatParticipation;
import com.example.malang.domain.ChatRoom;
import com.example.malang.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatParticipationResponseDto {

    private Long id;

    private Member member;

    private ChatRoom chatRoom;

    private List<Chat> chatList = new ArrayList<>();

    public ChatParticipationResponseDto(ChatParticipation chatParticipation) {
        this.id = chatParticipation.getId();
    }
}
