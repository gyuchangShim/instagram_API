package com.instagram.api.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostUpdateRequest {

    @NotNull
    private Long id;
    @NotBlank
    private String content;

//    private MultipartFile image;

}
