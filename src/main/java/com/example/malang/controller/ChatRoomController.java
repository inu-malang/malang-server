package com.example.malang.controller;

import com.example.malang.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/api/v1/rooms/{roomId}")
    public void deleteChatRoom(@PathVariable Long roomId) {
        log.info("[roomId] = {}", roomId);
        chatRoomService.deleteChatRoom(roomId);
    }
}
