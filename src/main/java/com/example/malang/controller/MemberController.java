package com.example.malang.controller;

import com.example.malang.config.base.BaseResponse;
import com.example.malang.dto.PostRequestDto;
import com.example.malang.dto.PostResponseDto;
import com.example.malang.service.MemberService;
import com.example.malang.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.malang.dto.MemberRequestDto.*;
import static com.example.malang.dto.MemberResponseDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;

    @PostMapping("/members/sign-up")
    public ResponseEntity<BaseResponse<LoginAuthenticationMember>> signUp(@RequestBody OauthLoginMember oAuthLoginMember) {
        return ResponseEntity.ok().body(new BaseResponse<>(memberService.signUp(oAuthLoginMember)));
    }

    /**
     * MyPage API
     */
    @GetMapping("/members/{member-id}/my-page")
    public ResponseEntity<BaseResponse<MyPage>> getMyPage(@PathVariable("member-id") Long memberId) {
        return ResponseEntity.ok().body(new BaseResponse<>(memberService.getMyPage(memberId)));
    }

    /**
     * MyPage -> 내가 작성한 게시글
     */
    // TODO 여기서부터 시작!
    @GetMapping("/members/{member-id}/my-page/my-post")
    public ResponseEntity<BaseResponse<PostResponseDto.PostDetailResponseDTO>> getMyPageForMyPost(@PathVariable(value = "member-id") Long memberId) {
        return ResponseEntity.ok().body(new BaseResponse<>(memberService.getMyPostFromMyPage(memberId)));
    }

    // 몇 개 생각해야할게 있음
    @PatchMapping(value = "/members/{member-id}/my-page/my-post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponse<Long>> modifyMyPageForMyPost(
            @PathVariable(value = "member-id") Long memberId,
            @RequestPart("postRequest") PostRequestDto.PostRequest postRequest,
            @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok().body(new BaseResponse<>(postService.modifyPost(memberId, postRequest, imageFile)));
    }




}
