package com.example.malang.controller;

import com.example.malang.config.BaseResponse;
import com.example.malang.domain.Post;
import com.example.malang.dto.PostDetailResponseDTO;
import com.example.malang.dto.PostRequest;
import com.example.malang.dto.PostResponseDTO;

import com.example.malang.service.MemberService;

import com.example.malang.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class PostController {

    private final PostService postService;


    // 게시글 생성
    @PostMapping(value = "/members/{memberId}/post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponse<Long>> createPost(@PathVariable Long memberId, @RequestPart PostRequest postRequest, @RequestPart MultipartFile imageFile) throws IOException {
            return ResponseEntity.ok(new BaseResponse<>(postService.createPost(memberId, postRequest, imageFile)));
    }


    // 게시글 리스트 조회
    @GetMapping("/posts")
    public ResponseEntity<BaseResponse<List<PostResponseDTO>>> findAll() {
        return ResponseEntity.ok().body(new BaseResponse<>(postService.findAllPost()));
    }


    // 게시글 단건 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<BaseResponse<PostDetailResponseDTO>> findById(@PathVariable Long postId) {
        return ResponseEntity.ok().body(new BaseResponse<>(postService.findPostById(postId)));
    }

}
