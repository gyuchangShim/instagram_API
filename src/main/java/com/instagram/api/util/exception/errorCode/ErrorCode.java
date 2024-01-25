package com.instagram.api.util.exception.errorCode;

import lombok.*;
import org.springframework.http.HttpStatus;


public interface ErrorCode {

    String name();
    HttpStatus getHttpStatus();
    String getMessage();

}
