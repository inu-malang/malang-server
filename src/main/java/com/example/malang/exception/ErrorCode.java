package com.example.malang.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * 성공 코드
     */
    SUCCESS(HttpStatus.OK, "success", "요청에 성공했습니다."),
    CREATED(HttpStatus.CREATED, "created", "요청에 성공했으며 리소스가 정상적으로 생성되었습니다"),
    ACCEPTED(HttpStatus.ACCEPTED, "accepted", "요청에 성공했으나 처리가 완료되지 않았습니다"),

    /**
     * 멤버 에러
     */
    NOT_EXIST_MEMBER(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 유저입니다."),
    ALREADY_EXIST_MEMBER(HttpStatus.CONFLICT, "M002", "이미 존재하는 유저입니다."),

    /**
     * 토큰 에러
     */


    TOKEN_NOT_VALID(HttpStatus.FORBIDDEN , "T001" , "토큰이 유효하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED , "T002" , "리프레쉬 토큰이 만료됐습니다."),


    /**
     * 게시글 에러
     */
    NOT_EXIST_POST(HttpStatus.NOT_FOUND, "P001", "존재하지 않는 게시물입니다."),
    INVALID_FILE_INPUT(HttpStatus.INTERNAL_SERVER_ERROR, "P002", "올바르지 않은 파일입니다."),

    /**
     * 장소 에러
     */
    NOT_EXIST_PLACE(HttpStatus.NOT_FOUND, "PL001", "존재하지 않는 장소입니다."),

    /**
     * 요청 에러
     */
    NOT_EXIST_REQUEST(HttpStatus.NOT_FOUND, "R001", "존재하지 않는 합석 요청입니다."),

    /**
     * 일반 오류 코드
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Z002", "잘못된 요청입니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Z003","찾을 수 없습니다"),
    NOT_SUPPORTED_METHOD_ERROR(HttpStatus.METHOD_NOT_ALLOWED, "Z004", "지원하지 않는 HTTP Method 요청입니다."),
    INVALID_PARAMETER_ERROR(HttpStatus.BAD_REQUEST, "Z005", "입력값이 유효하지 않습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Z500", "서버와의 연결에 실패했습니다");


    private final HttpStatus status;
    private final String code;
    private final String message;

    public static ErrorCode findByMessage(String message) {
        for (ErrorCode response : values()) {
            if (message.equals(response.message)) {
                return response;
            }
        }
        return null;
    }
}
