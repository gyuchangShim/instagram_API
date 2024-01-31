package com.instagram.api.reply.service;

import com.instagram.api.post.domain.Post;
import com.instagram.api.post.repository.PostRepository;
import com.instagram.api.reply.domain.Reply;
import com.instagram.api.reply.dto.request.ReplyCreateRequest;
import com.instagram.api.reply.dto.request.ReplyToReplyRequest;
import com.instagram.api.reply.dto.request.ReplyUpdateRequest;
import com.instagram.api.reply.dto.response.ReplyResponse;
import com.instagram.api.reply.repository.ReplyRepository;
import com.instagram.api.user.domain.User;
import com.instagram.api.user.service.UserService;
import com.instagram.api.util.exception.errorCode.ReplyErrorCode;
import com.instagram.api.util.exception.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        Reply reply = replyCreateRequest.toEntity(targetUser, targetPost);
        reply.plusDepth();
        replyRepository.save(reply);
    }

    @Transactional
    public void replyToReply(UUID id, ReplyToReplyRequest replyToReplyRequest, Reply parent) {
        User targetUser = userService.checkExist(id);
        if(parent.getDepth() >= 2) {
            throw new CustomException(ReplyErrorCode.OVER_COUNT_REPLIES);
        }
        parent.plusDepth();
        Reply reply = replyToReplyRequest.toEntity(targetUser, parent);
        replyRepository.save(reply);
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
        Reply targetReply = checkExist(replyUpdateRequest.getReply_id());
        User targetUser = userService.checkExist(id);
        if(targetReply.getUser().equals(targetUser)) {
            targetReply.update(replyUpdateRequest.getContent());
        }
    }

    @Transactional
    public void deleteReply(UUID user_id, Long id) {
        Reply targetReply = checkExist(id);
        if(targetReply.getParent() != null) {
            targetReply.getParent().minusDepth();
        }
        replyRepository.deleteAllByParent(targetReply);
        replyRepository.delete(targetReply);
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
                .orElseThrow(() -> new CustomException(ReplyErrorCode.REPLY_NOT_FOUND));
    }

}
