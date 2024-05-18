package com.example.malang.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.malang.domain.Place;
import com.example.malang.domain.Post;
import com.example.malang.domain.member.Member;
import com.example.malang.dto.PostRequestDto;
import com.example.malang.dto.PostResponseDto;
import com.example.malang.exception.BaseException;
import com.example.malang.exception.ErrorCode;
import com.example.malang.repository.MemberRepository;
import com.example.malang.repository.PlaceRepository;
import com.example.malang.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.malang.dto.PostResponseDto.PostDetailResponseDTO;
import static com.example.malang.dto.PostResponseDto.PostListResponseDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final AmazonS3Client amazonS3Client;


    /**
     * S3
     */
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId);
    }


    /**
     * 전체 삭제 메서드
     */
    @Transactional
    public String deleteAll() {
        postRepository.deleteAll();
        return "전체 삭제 완료";
    }

    /**
     * 테스트용 image만 받는 메서드
     */
    @Transactional
    public PostResponseDto.PostImageResponseDTO createPostWithImage(MultipartFile imageFile) throws IOException {
        //파일의 원본 이름
        String originalFileName = imageFile.getOriginalFilename();
        //DB에 저장될 파일 이름
        String storeFileName = createStoreFileName(originalFileName);

        //S3에 저장
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imageFile.getContentType());
        metadata.setContentLength(imageFile.getSize());
        amazonS3Client.putObject(bucket, storeFileName, imageFile.getInputStream(), metadata);

        return new PostResponseDto.PostImageResponseDTO(originalFileName, storeFileName);
    }

    /**
     * 테스트용 json만 받는 메서드
     */
    @Transactional
    public Long createPostWithJson(Long memberId, PostRequestDto.PostRequest postRequest) {
        Place place = Place.from(postRequest);
        placeRepository.save(place);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_MEMBER));

        Post post = Post.of(postRequest, place, member);

        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional
    public Long createPost(Long memberId, PostRequestDto.PostRequest postRequest, MultipartFile imageFile) throws IOException {
        //파일의 원본 이름
        String originalFileName = imageFile.getOriginalFilename();
        //DB에 저장될 파일 이름
        String storeFileName = createStoreFileName(originalFileName);

        //S3에 저장
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imageFile.getContentType());
        metadata.setContentLength(imageFile.getSize());
        amazonS3Client.putObject(bucket, storeFileName, imageFile.getInputStream(), metadata);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_MEMBER));

        Place place = Place.from(postRequest);
        placeRepository.save(place);

        Post post = Post.of(postRequest, place, member);

        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    // 리스트 조회
    public List<PostListResponseDTO> findAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostListResponseDTO::new)
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
}
