package com.example.malang.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRequest {

    private String content;

    private String sender;

    private LocalDateTime timestamp;
}
