package com.instagram.api.util.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{

    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않은 사용자입니다."),
    DUPLICATE_ID(HttpStatus.CONFLICT, "해당 아이디는 사용할 수 없습니다."),
    ERROR_FOLLOW_MYSELF(HttpStatus.BAD_REQUEST, "자기 자신은 팔로우할 수 없습니다."),
    DUPLICATE_FOLLOW(HttpStatus.CONFLICT, "이미 팔로우 한 대상입니다."),
    ERROR_USER_PROFILE(HttpStatus.BAD_REQUEST, "사용자 접근 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
