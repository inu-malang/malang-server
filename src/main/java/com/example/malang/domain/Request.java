package com.example.malang.domain;

import com.example.malang.config.BaseEntity;
import com.example.malang.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Request extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    private String message;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    // 요청보낸 Member
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    // 요청 받는 Member -> Post.Member
    private Post post;

    @OneToOne(mappedBy = "request", fetch = LAZY)
    private ChatRoom chatRoom;

    @Builder
    public Request(String message, Member member, Post post) {
        this.message = message;
        this.status = RequestStatus.WAIT;
        this.member = member;
        this.post = post;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
