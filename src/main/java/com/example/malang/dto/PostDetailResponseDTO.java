package com.example.malang.dto;

import com.example.malang.domain.Place;
import com.example.malang.domain.Post;
import com.example.malang.domain.member.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostDetailResponseDTO {
    private Long id;
    private String title;

    private String content;

    private String memberName;

    private String uploadFileName;

    private String storeFileName;

    public PostDetailResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.memberName = post.getMember().getName();
        this.uploadFileName = post.getUploadFileName();
        this.storeFileName = post.getStoreFileName();
    }
}
