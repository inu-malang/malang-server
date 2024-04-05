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

    /**
     * chatParticipationId 의 아이디로 호출이 오면 메서드 호출
     * SendTo 는 메서드의 결과(ChatResponse) 를 토픽(채팅 참여자)에게 전달
     */
    @MessageMapping("/hello/{chatParticipationId}")
    @SendTo("/topic/greetings/{chatParticipationId}")
    public ChatResponse chat(@DestinationVariable Long chatParticipationId, ChatRequest chatRequest) {
        log.info(String.valueOf(chatParticipationId));
        return chatService.createChat(chatParticipationId, chatRequest);
    }
}
