package com.instagram.api.post.dto.response;

import com.instagram.api.post.domain.Post;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PostResponse {

    private UUID id;
    private Long post_id;
    private String content;
//    private String image;

    public PostResponse(UUID id, Long post_id, String content) {
        this.id = id;
        this.post_id = post_id;
        this.content = content;
    }

    public static PostResponse from(Post post) {
        return new PostResponse(post.getUser().getId(), post.getId(), post.getContent());
    }

    /**
     * [이미지 저장 가능]
    public PostResponse(UUID id, Long post_id, String content, String image) {
        this.id = id;
        this.post_id = post_id;
        this.content = content;
        this.image = image;
    }

    public static PostResponse from(Post post) {
        return new PostResponse(post.getUser().getId(), post.getId(), post.getContent(), post.getImage());
    }
     */


}
