package com.instagram.api.reply.dto.request;

import com.instagram.api.post.domain.Post;
import com.instagram.api.reply.domain.Reply;
import com.instagram.api.user.domain.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReplyUpdateRequest {

    @NotNull
    @Column(name = "post_id")
    private Long id;

    @NotBlank
    private String content;

}
