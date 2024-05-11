package com.example.malang.service;

import com.example.malang.domain.Place;
import com.example.malang.domain.Post;
import com.example.malang.dto.PlaceResponseDto;
import com.example.malang.dto.PostResponseDto;
import com.example.malang.exception.BaseException;
import com.example.malang.exception.ErrorCode;
import com.example.malang.repository.PlaceRepository;
import com.example.malang.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PostRepository postRepository;

    public PlaceResponseDto.PlaceResponse convertToDTO(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_PLACE));

        return PlaceResponseDto.PlaceResponse.from(place);
    }

    public List<PostResponseDto.PostListResponseDTO> findPostByPlaceName(String placeName) {
        List<Post> posts = postRepository.findAllByPlaceName(placeName);
        return posts.stream()
                .map(PostResponseDto.PostListResponseDTO::new)
                .collect(Collectors.toList());
    }
}
