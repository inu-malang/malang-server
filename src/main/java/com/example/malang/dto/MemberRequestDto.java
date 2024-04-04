package com.example.malang.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

public class MemberRequestDto {

    @Getter @Builder
    public static class OauthLoginMember {
        /**
         * 카카오 회원가입 + 로그인 후 넘어오는 값을 여기에 추가하며 됩니다.
         * 필드가 1개만 있을 경우 POSTMAN 오류가 발생해서 userId 로 일단 필드 2개 유지합니다.
         */
        private String email;
        private String name;
    }
}
