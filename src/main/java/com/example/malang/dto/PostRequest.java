package com.example.malang.dto;


import com.example.malang.domain.Place;
import com.example.malang.domain.member.Member;
import lombok.Getter;


@Getter
public class PostRequest {
    private String title;

    private String content;

    /*
    TODO: Place 관련 내용 수정 필수
     */
}
