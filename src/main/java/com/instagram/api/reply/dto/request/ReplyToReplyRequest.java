package com.instagram.api.reply.dto.request;

import com.instagram.api.post.domain.Post;
import com.instagram.api.reply.domain.Reply;
import com.instagram.api.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReplyToReplyRequest {

    @Schema(description = "댓글 ID")
    @NotNull
    private Long parent_id;

    @Schema(description = "답글 내용")
    @NotBlank
    private String content;

    public Reply toEntity(User user, Reply parent) {
        return Reply.builder()
                .user(user)
                .content(this.content)
                .post(parent.getPost())
                .reply(parent)
                .build();
    }
}
