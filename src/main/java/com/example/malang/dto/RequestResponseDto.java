package com.example.malang.dto;

import com.example.malang.domain.Request;
import com.example.malang.domain.RequestStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestResponseDto {

    private Long id;

    private String message;

    private RequestStatus status;

    // 요청보낸 Member
    private Long memberId;

    // 요청 받는 Member -> Post.Member
    private Long postId;

    private Long chatRoomId;


    public RequestResponseDto(Request request) {
        this.id = request.getId();
        this.message = request.getMessage();
        this.status = request.getStatus();
        this.memberId = request.getMember().getId();
        this.postId = request.getPost().getId();
        if (status.equals(RequestStatus.WAIT)) {
            this.chatRoomId = null;
        } else {
            this.chatRoomId = request.getChatRoom().getId();
        }

    }
}
