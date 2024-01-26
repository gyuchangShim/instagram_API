package com.instagram.api.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.instagram.api.user.domain.User;
import com.instagram.api.user.state.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRegistRequest {

    @NotBlank
    private String uid;

    @NotBlank
    private String pw;

    @NotBlank
    private String name;

    @NotNull
    private int age;

    @NotBlank
    private String phoneNumber;

    @Builder
    private UserRegistRequest(@JsonProperty("uid") String uid, @JsonProperty("pw") String pw,
                              @JsonProperty("name") String name, @JsonProperty("age") int age,
                              @JsonProperty("phoneNumber") String phoneNumber) {
        this.uid = uid;
        this.pw = pw;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

//    public User toEntity(String fileName, String imageUrl, String pw) {
//        return User.builder()
//                .uid(this.uid)
//                .pw(pw)
//                .name(this.name)
//                .age(this.age)
//                .phoneNumber(this.phoneNumber)
//                .originalFileName(fileName)
//                .imageUrl(imageUrl)
//                .role(Role.USER)
//                .build();
//    }

    public User toEntity(String pw) {
        return User.builder()
                .uid(this.uid)
                .pw(pw)
                .name(this.name)
                .age(this.age)
                .phoneNumber(this.phoneNumber)
                .role(Role.USER)
                .build();
    }
}
