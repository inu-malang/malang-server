package com.example.malang.controller;

import com.example.malang.dto.ChatRequest;
import com.example.malang.dto.ChatResponse;
import com.example.malang.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/hello/{chatParticipationId}")
    @SendTo("/topic/greetings/{chatParticipationId}")
    public ChatResponse chat(@DestinationVariable Long chatParticipationId, ChatRequest chatRequest) {
        log.info(String.valueOf(chatParticipationId));
        return chatService.createChat(chatParticipationId, chatRequest);
    }
}
