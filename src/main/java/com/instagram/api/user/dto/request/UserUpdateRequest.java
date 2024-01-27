package com.instagram.api.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Schema(description = "사용자 정보 수정 DTO")
@Getter
public class UserUpdateRequest {

    @Length(min = 6, max = 20)
    @Schema(description = "비밀번호")
    @NotNull
    private String pw;

    @Schema(description = "이름")
    private String name;

    @Range(min = 1, max = 99)
    @Schema(description = "나이")
    private int age;

    @Schema(description = "전화번호", example = "000-0000-0000")
    private String phoneNumber;


}
