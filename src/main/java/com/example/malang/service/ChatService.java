package com.example.malang.service;

import com.example.malang.domain.Chat;
import com.example.malang.domain.ChatParticipation;
import com.example.malang.dto.ChatRequest;
import com.example.malang.dto.ChatResponse;
import com.example.malang.repository.ChatParticipationRepository;
import com.example.malang.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final ChatParticipationRepository chatParticipationRepository;

    public ChatResponse createChat(Long chatParticipationId, ChatRequest chatRequest) {
        ChatParticipation chatParticipation = chatParticipationRepository.findById(chatParticipationId)
                .orElseThrow(() -> new RuntimeException());

        Chat chat = Chat.createChat(chatParticipation, chatRequest);
        chatRepository.save(chat);
        log.info("[createChat] 채팅 생성 완료");

        return ChatResponse.createChatResponse(chatRequest);
    }
}
