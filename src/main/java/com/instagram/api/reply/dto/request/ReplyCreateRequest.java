package com.instagram.api.reply.dto.request;

import com.instagram.api.post.domain.Post;
import com.instagram.api.reply.domain.Reply;
import com.instagram.api.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplyCreateRequest {

    @NotNull @Column(name = "post_id")
    private Long id;

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
