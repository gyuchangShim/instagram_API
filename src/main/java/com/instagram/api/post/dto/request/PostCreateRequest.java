package com.instagram.api.post.dto.request;

import com.instagram.api.post.domain.Post;
import com.instagram.api.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
public class PostCreateRequest {

    @NotNull
    private UUID id;
    @NotBlank
    private String content;

//    private MultipartFile image;

    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .content(content)
                .build();
    }

//    public Post toEntity(User user, String image) {
//        return Post.builder()
//                .user(user)
//                .content(content)
//                .image(image)
//                .build();
//    }

}
