package com.example.malang.controller;

import com.example.malang.config.BaseResponse;
import com.example.malang.dto.RequestRequestDto;
import com.example.malang.dto.RequestResponseDto;
import com.example.malang.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class RequestController {

    private final RequestService requestService;

    // 요청 보내기
    @PostMapping("/members/{memberId}/posts/{postId}/requests")
    public ResponseEntity<BaseResponse<Long>> createRequest(@PathVariable("memberId") Long memberId, @PathVariable("postId") Long postId, @RequestBody RequestRequestDto requestRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(requestService.createRequest(memberId, postId, requestRequestDto)));
    }

    // 요청 단건 조회
    @GetMapping("/requests/{requestId}")
    public ResponseEntity<BaseResponse<RequestResponseDto>> findById(@PathVariable("requestId") Long requestId) {
        return ResponseEntity.ok().body(new BaseResponse<>(requestService.findById(requestId)));
    }

    // 게시물별 요청 리스트 조회
    @GetMapping("/posts/{postId}/requests")
    public ResponseEntity<BaseResponse<List<RequestResponseDto>>> findAllByPost(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok().body(new BaseResponse<>(requestService.findAllByPost(postId)));
    }

    // 회원이 보낸 요청 목록
    @GetMapping("/members/{memberId}/requests")
    public ResponseEntity<BaseResponse<List<RequestResponseDto>>> findAllByMember(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok().body(new BaseResponse<>(requestService.findAllByMember(memberId)));
    }
}
