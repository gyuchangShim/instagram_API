package com.instagram.api.reply.service;

import com.instagram.api.post.domain.Post;
import com.instagram.api.post.repository.PostRepository;
import com.instagram.api.post.service.PostService;
import com.instagram.api.reply.domain.Reply;
import com.instagram.api.reply.dto.request.ReplyCreateRequest;
import com.instagram.api.reply.dto.request.ReplyUpdateRequest;
import com.instagram.api.reply.dto.response.ReplyResponse;
import com.instagram.api.reply.repository.ReplyRepository;
import com.instagram.api.user.domain.User;
import com.instagram.api.user.repository.UserRepository;
import com.instagram.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final UserService userService;
    private final PostRepository postRepository;

    @Transactional
    public void createReply(UUID id, ReplyCreateRequest replyCreateRequest) {
        User targetUser = userService.checkExist(id);
        Post targetPost = postRepository.findById(replyCreateRequest.getId()).get();
        replyRepository.save(replyCreateRequest.toEntity(targetUser, targetPost));
    }

    public List<ReplyResponse> retrieveReplyByPost(Long id) {
        Post targetPost = postRepository.findById(id).get();
        return replyRepository.findAllByPost(targetPost)
                .stream().map(ReplyResponse::from)
                .collect(Collectors.toList());
    }

    public ReplyResponse retrieveReplyById(Long reply_id) {
        return ReplyResponse.from(checkExist(reply_id));
    }

    @Transactional
    public void updateReply(UUID id, ReplyUpdateRequest replyUpdateRequest) {
        Reply targetReply = checkExist(replyUpdateRequest.getId());
        User targetUser = userService.checkExist(id);
        if(targetReply.getUser().equals(targetUser)) {
            targetReply.update(replyUpdateRequest.getContent());
        }
    }

    @Transactional
    public void deleteReply(UUID user_id, Long id) {
        Reply targetReply = checkExist(id);
        if(targetReply.getUser().getId().equals(user_id)) {
            replyRepository.delete(targetReply);
        }
    }

    @Transactional
    public void deleteAllByUser(User targetUser) {
        replyRepository.deleteAllByUser(targetUser);
    }

    @Transactional
    public void deleteAllByPost(Post targetPost) {
        replyRepository.deleteAllByPost(targetPost);
    }

    public Reply checkExist(Long id) {
        return replyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 댓글이 존재하지 않습니다."));
    }


}
