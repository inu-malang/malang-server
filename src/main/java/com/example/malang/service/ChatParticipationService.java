package com.example.malang.service;

import com.example.malang.domain.ChatParticipation;
import com.example.malang.domain.ChatRoom;
import com.example.malang.domain.member.Member;
import com.example.malang.repository.ChatParticipationRepository;
import com.example.malang.repository.ChatRoomRepository;
import com.example.malang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatParticipationService {

     private final ChatParticipationRepository chatParticipationRepository;

    private final MemberRepository memberRepository;

    private final ChatRoomRepository chatRoomRepository;

    public List<ChatParticipation> findByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException());

        List<ChatParticipation> chatParticipationList = member.getChatParticipationList();
        return chatParticipationList;
    }

    public List<ChatParticipation> findByRoomId(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException());

        List<ChatParticipation> chatParticipationList = chatRoom.getChatParticipationList();

        return chatParticipationList;
    }
}
