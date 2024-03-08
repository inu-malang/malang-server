package com.example.malang.controller;

import com.example.malang.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/api/v1/requests/{requestId}/rooms")
    public Long createChatRoom(@PathVariable Long requestId) {
        log.info("requestId : {}", requestId);
        Long createChatRoom = chatRoomService.createChatRoom(requestId);
        log.info("createChatRoom : {}", createChatRoom);
        return createChatRoom;
    }
}
