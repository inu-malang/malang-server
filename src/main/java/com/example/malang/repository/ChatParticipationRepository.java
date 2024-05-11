package com.example.malang.repository;

import com.example.malang.domain.ChatParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatParticipationRepository extends JpaRepository<ChatParticipation, Long> {

    List<ChatParticipation> findByMemberId(Long memberId);

    ChatParticipation findByMemberIdAndChatRoomId(Long memberId, Long roomId);
}
