package com.example.malang.controller;

import com.example.malang.config.base.BaseResponse;
import com.example.malang.dto.PostRequestDto;
import com.example.malang.dto.PostResponseDto;
import com.example.malang.dto.RequestResponseDto;
import com.example.malang.service.MemberService;
import com.example.malang.service.PostService;
import com.example.malang.service.RequestService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.malang.dto.MemberRequestDto.*;
import static com.example.malang.dto.MemberResponseDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;
    private final RequestService requestService;

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
    @GetMapping("/members/{member-id}/my-page/my-post")
    public ResponseEntity<BaseResponse<PostResponseDto.PostDetailResponseDTO>> getMyPageForMyPost(@PathVariable(value = "member-id") Long memberId) {
        return ResponseEntity.ok().body(new BaseResponse<>(memberService.getMyPostFromMyPage(memberId)));
    }

    /**
     * MyPage -> 내가 작성한 게시글 -> 수정
     */
    // 몇 개 생각해야할게 있음
    @PatchMapping(value = "/members/{member-id}/my-page/my-post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponse<Long>> modifyMyPageForMyPost(
            @PathVariable(value = "member-id") Long memberId,
            @RequestPart("postRequest") PostRequestDto.PostRequest postRequest,
            @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok().body(new BaseResponse<>(postService.modifyPost(memberId, postRequest, imageFile)));
    }

    /**
     * MyPage -> 나의 신청 정보
     * 남기가 RequestController 작성한거 그대로 가지고 오고 Mapping 만 변경
     */
    @GetMapping("/members/{memberId}/my-page/my-requests")
    public ResponseEntity<BaseResponse<List<RequestResponseDto>>> findAllByMember(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok().body(new BaseResponse<>(requestService.findAllByMember(memberId)));
    }
}
