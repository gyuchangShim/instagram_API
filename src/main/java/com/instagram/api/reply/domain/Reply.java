package com.instagram.api.reply.domain;

import com.instagram.api.post.domain.Post;
import com.instagram.api.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @CreationTimestamp
    private Timestamp createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply parent;

    private int depth;

    @Builder
    public Reply(String content, User user, Post post, Reply reply) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.parent = reply;
    }

    public void update(String content) {
        this.content = content;
    }

    public void plusDepth() {
        this.depth += 1;
    }

    public void minusDepth() {
        this.depth -= 1;
    }
}
