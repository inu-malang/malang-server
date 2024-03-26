package com.example.malang.service;

import com.example.malang.domain.Place;
import com.example.malang.domain.Post;
import com.example.malang.domain.member.Member;
import com.example.malang.dto.PostDetailResponseDTO;
import com.example.malang.dto.PostRequest;
import com.example.malang.dto.PostResponseDTO;
import com.example.malang.exception.BaseException;
import com.example.malang.exception.ErrorCode;
import com.example.malang.exception.ErrorResponse;
import com.example.malang.repository.MemberRepository;
import com.example.malang.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 파일 저장 경로
     */
    @Value("${FILE_DIR}")
    private String fileDir;

    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId);
    }

    @Transactional
    public Long createPost(Long memberId, PostRequest postRequest, MultipartFile imageFile) throws IOException {
        //파일의 원본 이름
        String originalFileName = imageFile.getOriginalFilename();
        //DB에 저장될 파일 이름
        String storeFileName = createStoreFileName(originalFileName);

        //실제 디렉토리에 파일로 저장
        imageFile.transferTo(new File(fileDir + storeFileName));


        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_USER));

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .member(member)
                .place(null)    //TODO
                .uploadFileName(originalFileName)
                .storeFileName(storeFileName)
                .build();

        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    // 리스트 조회
    public List<PostResponseDTO> findAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 단건 조회
    public PostDetailResponseDTO findPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_POST));

        return new PostDetailResponseDTO(post);
    }

    /**
     * 파일명이 겹치는 것을 방지하기위해 중복되지않는 UUID를 생성해서 반환(ext는 확장자)
     */
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    /**
     * 파일 확장자를 추출하기 위해 만든 메서드
     */
    private String extractExt(String originalFilename) {
        int post = originalFilename.lastIndexOf(".");
        return originalFilename.substring(post + 1);
    }

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }
}
