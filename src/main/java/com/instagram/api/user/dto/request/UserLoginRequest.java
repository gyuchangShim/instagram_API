package com.instagram.api.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginRequest {

    private String uid;
    private String pw;

    @Builder
    public UserLoginRequest(@JsonProperty("uid") String uid, @JsonProperty("pw") String pw) {
        this.uid = uid;
        this.pw = pw;
    }

}
