package com.instagram.api.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostUpdateRequest {

    @Schema(description = "수정하고자 하는 게시글 ID")
    @NotNull
    private Long id;

    @Schema(description = "게시글 내용")
    @NotBlank
    private String content;

    //    @Schema(description = "게시글 사진")
    //    private MultipartFile image;

}
