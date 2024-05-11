package com.example.malang.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatResponse {

    private String content;

    private String sender;

    private LocalDateTime timestamp;

    public static ChatResponse createChatResponse(ChatRequest chatRequest) {
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.content = chatRequest.getContent();
        chatResponse.sender = chatRequest.getSender();
        chatResponse.timestamp = chatRequest.getTimestamp();

        return chatResponse;
    }
}
