package com.instagram.api.util.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReplyErrorCode implements ErrorCode{
    OVER_COUNT_REPLIES(HttpStatus.BAD_REQUEST, "답글에는 답글을 달 수 없습니다."),
    REPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
