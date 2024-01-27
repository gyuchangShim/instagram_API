package com.instagram.api.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@Schema(description = "로그인 요청 DTO")
public class UserLoginRequest {

    @Schema(description = "아이디", defaultValue = "test")
    @NotBlank
    private String uid;

    @Length(min = 6, max = 20)
    @Schema(description = "비밀번호", defaultValue = "test2")
    @NotBlank
    private String pw;

    @Builder
    public UserLoginRequest(@JsonProperty("uid") String uid, @JsonProperty("pw") String pw) {
        this.uid = uid;
        this.pw = pw;
    }

}
