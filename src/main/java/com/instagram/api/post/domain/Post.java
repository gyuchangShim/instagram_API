package com.instagram.api.post.domain;

import com.instagram.api.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;
//    private String image;

    @Builder
    public Post(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public void updatePost(String content) {
        this.content = content;
    }

    /**
     *
     [이미지 저장 가능]
    @Builder
    public Post(User user, String content, String image) {
        this.user = user;
        this.content = content;
        this.image = image;
    }

    public void updatePost(String content, String image) {
        this.content = content;
        this.image = image;
    }
    */

}
