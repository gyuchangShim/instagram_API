package com.instagram.api.user.dto.response;

import com.instagram.api.user.state.Role;
import lombok.Builder;

@Builder
public record UserLoginResponse(
        String name, Role role, String token
) {

}
