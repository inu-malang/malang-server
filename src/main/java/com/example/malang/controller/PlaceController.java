package com.example.malang.controller;

import com.example.malang.config.BaseResponse;
import com.example.malang.dto.PlaceResponseDto;
import com.example.malang.dto.PostResponseDto;
import com.example.malang.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PlaceController {

    private final PlaceService placeService;

    // 장소 정보 조회
    @GetMapping("/places/{placeId}")
    public ResponseEntity<BaseResponse<PlaceResponseDto.PlaceResponse>> findPlaceById(@PathVariable("placeId") Long placeId) {
        return ResponseEntity.ok().body(new BaseResponse<>(placeService.convertToDTO(placeId)));
    }

    // 장소별 게시물 조회
    @GetMapping("/postsByPlace")
    public ResponseEntity<BaseResponse<List<PostResponseDto.PostListResponseDTO>>> findAllPostByPlaceName(@RequestParam("placeName") String placeName) {
        return ResponseEntity.ok().body(new BaseResponse<>(placeService.findPostByPlaceName(placeName)));
    }
}
