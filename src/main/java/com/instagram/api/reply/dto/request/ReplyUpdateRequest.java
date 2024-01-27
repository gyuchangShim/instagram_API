package com.instagram.api.reply.dto.request;

import com.instagram.api.post.domain.Post;
import com.instagram.api.reply.domain.Reply;
import com.instagram.api.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReplyUpdateRequest {

    @Schema(description = "댓글 ID")
    @NotNull
    private Long reply_id;

    @Schema(description = "답글 내용")
    @NotBlank
    private String content;

}
