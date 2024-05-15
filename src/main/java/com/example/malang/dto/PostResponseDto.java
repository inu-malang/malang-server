package com.example.malang.dto;

import com.example.malang.domain.Post;
import lombok.Getter;

public class PostResponseDto {

    @Getter
    public static class PostDetailResponseDTO {

        private Long id;

        private String title;

        private Long placeId;

        private String content;

        private String memberName;

        private String uploadFileName;

        private String storeFileName;

        public PostDetailResponseDTO(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.placeId = post.getPlace().getId();
            this.content = post.getContent();
            this.memberName = post.getMember().getName();
            this.uploadFileName = post.getUploadFileName();
            this.storeFileName = post.getStoreFileName();
        }
    }

    @Getter
    public static class PostListResponseDTO {

        private Long id;

        private String title;

        private String placeName;

        private String uploadFileName;

        private String storeFileName;

        public PostListResponseDTO(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.placeName = post.getPlace().getName();
            this.uploadFileName = post.getUploadFileName();
            this.storeFileName = post.getStoreFileName();
        }

    }

    @Getter
    public static class PostImageResponseDTO {

        private String uploadFileName;
        private String storeFileName;

        public PostImageResponseDTO(String uploadFileName, String storeFileName) {
            this.uploadFileName = uploadFileName;
            this.storeFileName = storeFileName;
        }
    }
}
