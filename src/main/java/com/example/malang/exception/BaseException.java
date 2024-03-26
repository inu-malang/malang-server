package com.example.malang.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public BaseException(ErrorCode code) {
        this.httpStatus = code.getStatus();
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
