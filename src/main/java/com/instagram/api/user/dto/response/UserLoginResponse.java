package com.instagram.api.user.dto.response;

import com.instagram.api.user.state.Role;

public record UserLoginResponse(
        String name, Role role, String token
) {

}
