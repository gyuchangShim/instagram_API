package com.instagram.api.reply.repository;

import com.instagram.api.post.domain.Post;
import com.instagram.api.reply.domain.Reply;
import com.instagram.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByPost(Post targetPost);

    void deleteAllByUser(User targetUser);

    void deleteAllByPost(Post targetPost);

    void deleteAllByParent(Reply targetReply);
}
