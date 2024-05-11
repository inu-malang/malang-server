package com.example.malang.service;

import com.example.malang.domain.Post;
import com.example.malang.domain.member.Member;
import com.example.malang.dto.PostResponseDto;
import com.example.malang.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.malang.dto.MainResponseDto.MainRequiredInfo;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainService {

    private final PostRepository postRepository;

    public MainRequiredInfo getMainInfo(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto.PostListResponseDTO> postDtoList = posts.stream()
                .map(PostResponseDto.PostListResponseDTO::new)
                .toList();

        return MainRequiredInfo.toDto(member.getName() , postDtoList);
    }
}
