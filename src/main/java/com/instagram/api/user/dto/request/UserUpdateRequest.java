package com.instagram.api.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    @NotNull
    private String pw;

    private String name;

    private int age;

    private String phoneNumber;


}
