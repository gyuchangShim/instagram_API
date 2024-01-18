package com.instagram.api.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    @NotNull
    private Long id;

    private String name;

    private int age;

    private String phoneNumber;


}
