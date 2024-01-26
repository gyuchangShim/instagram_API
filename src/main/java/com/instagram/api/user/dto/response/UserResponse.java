package com.instagram.api.user.dto.response;

import com.instagram.api.user.domain.User;
import com.instagram.api.user.state.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private String uid;
    private String pw;
    private String name;
    private int age;
    private String phoneNumber;
    private Role role;

    public UserResponse(String uid, String pw, String name, int age, String phoneNumber, Role role) {
        this.uid = uid;
        this.pw = pw;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getUid(), user.getPw(), user.getName(), user.getAge(), user.getPhoneNumber(), user.getRole());
    }

}
