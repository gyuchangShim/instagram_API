package com.instagram.api.reply.dto.response;

import com.instagram.api.post.domain.Post;
import com.instagram.api.reply.domain.Reply;
import com.instagram.api.user.domain.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

public class ReplyResponse {


    private Long id;

    private String content;

    private Timestamp createTime;

    private String name;

    private Long post_id;

    public ReplyResponse(Long id, String content, Timestamp time, String name, Long post_id) {
        this.id = id;
        this.content = content;
        this.createTime = time;
        this.name = name;
        this.post_id = post_id;
    }

    public static ReplyResponse from(Reply reply) {
        return new ReplyResponse(reply.getId(), reply.getContent(),
                reply.getCreateTime(), reply.getUser().getName(), reply.getPost().getId());
    }

}
