package com.instagram.api.reply.dto.request;

import com.instagram.api.post.domain.Post;
import com.instagram.api.reply.domain.Reply;
import com.instagram.api.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@Schema(description = "댓글 생성 DTO")
public class ReplyCreateRequest {

    @Schema(description = "게시글 ID")
    @NotNull
    private Long id;

    @Length
    @Schema(description = "댓글 내용")
    @NotBlank
    private String content;

    public Reply toEntity(User user, Post post) {
        return Reply.builder()
                .user(user)
                .content(this.content)
                .post(post)
                .build();
    }


}
