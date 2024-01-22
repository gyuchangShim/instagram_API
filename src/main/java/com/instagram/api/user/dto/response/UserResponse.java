package com.instagram.api.user.dto.response;

import com.instagram.api.user.domain.User;
import com.instagram.api.user.state.Role;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserResponse {

    private UUID id;
    private String name;
    private int age;
    private String phoneNumber;
    private Role role;

    public UserResponse(UUID id, String name, int age, String phoneNumber, Role role) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getPhoneNumber(), user.getRole());
    }

}
