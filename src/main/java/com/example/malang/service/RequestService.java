package com.example.malang.service;

import com.example.malang.domain.Post;
import com.example.malang.domain.Request;
import com.example.malang.domain.member.Member;
import com.example.malang.dto.RequestRequestDto;
import com.example.malang.dto.RequestResponseDto;
import com.example.malang.exception.BaseException;
import com.example.malang.exception.ErrorCode;
import com.example.malang.repository.MemberRepository;
import com.example.malang.repository.PostRepository;
import com.example.malang.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestService {

    private final RequestRepository requestRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 요청 생성
    @Transactional
    public Long createRequest(Long memberId, Long postId, RequestRequestDto requestRequestDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_MEMBER));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_POST));

        Request request = Request.of(requestRequestDto, member, post);
        Request savedPost = requestRepository.save(request);

        post.getRequests().add(request);
        member.getRequests().add(request);

        return savedPost.getId();
    }

    // 요청 조회
    public RequestResponseDto findById(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_REQUEST));

        return new RequestResponseDto(request);
    }

    // 게시글별 요청 목록
    public List<RequestResponseDto> findAllByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_POST));
        List<Request> requests = post.getRequests();
        return requests.stream()
                .map(RequestResponseDto::new)
                .collect(Collectors.toList());
    }

    // 회원이 보낸 요청 목록
    public List<RequestResponseDto> findAllByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_MEMBER));
        List<Request> requests = member.getRequests();
        return requests.stream()
                .map(RequestResponseDto::new)
                .collect(Collectors.toList());
    }

}
