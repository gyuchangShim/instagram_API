package com.instagram.api.util.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터가 적합하지 않습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
