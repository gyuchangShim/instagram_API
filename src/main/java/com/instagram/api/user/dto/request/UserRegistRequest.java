package com.instagram.api.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.instagram.api.user.domain.User;
import com.instagram.api.user.state.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Schema(description = "회원가입 요청 DTO")
@Getter
public class UserRegistRequest {

    @Schema(description = "아이디", defaultValue = "test")
    @NotBlank
    private String uid;

    @Length(min = 6, max = 20)
    @Schema(description = "비밀번호", defaultValue = "test2")
    @NotBlank
    private String pw;

    @Schema(description = "이름", defaultValue = "COW")
    @NotBlank
    private String name;

    @Range(min = 1, max = 99)
    @Schema(description = "나이")
    private int age;

    @Schema(description = "전화번호", example = "000-0000-0000")
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
