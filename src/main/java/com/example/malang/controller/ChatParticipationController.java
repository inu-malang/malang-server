package com.example.malang.controller;

import com.example.malang.domain.ChatParticipation;
import com.example.malang.dto.ChatParticipationResponseDto;
import com.example.malang.service.ChatParticipationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatParticipationController {

    private final ChatParticipationService chatParticipationService;

    @GetMapping("/api/v1/members/{memberId}/chat-participations")
    public List<ChatParticipation> findByMemberId(@PathVariable Long memberId) {
        log.info("[findByMemberId] {}번 회원의 채팅방 목록 조회를 합니다.", memberId);
        List<ChatParticipation> chatParticipationList = chatParticipationService.findByMemberId(memberId);

        return chatParticipationList;
//        return chatParticipationList.stream()
//                .map(chatParticipation -> new ChatParticipationResponseDto(chatParticipation))
//                .collect(Collectors.toList());
    }

    @GetMapping("/api/v1/rooms/{roomId}/chat-participations")
    public List<ChatParticipation> findByMemberIdAndRoomId(@PathVariable Long roomId) {
        log.info("[findByRoomId] {}번 채팅방 조회를 합니다.", roomId);
        List<ChatParticipation> chatParticipationList = chatParticipationService.findByRoomId(roomId);

        return chatParticipationList;
    }
}
