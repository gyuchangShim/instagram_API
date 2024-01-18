package com.instagram.api.user.dto.request;

import lombok.Getter;

@Getter
public class UserLoginRequest {

    private String uid;
    private String pw;

}
