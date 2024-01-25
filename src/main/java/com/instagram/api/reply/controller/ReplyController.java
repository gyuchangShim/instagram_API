package com.instagram.api.reply.controller;

import com.instagram.api.reply.domain.Reply;
import com.instagram.api.reply.dto.request.ReplyCreateRequest;
import com.instagram.api.reply.dto.request.ReplyToReplyRequest;
import com.instagram.api.reply.dto.request.ReplyUpdateRequest;
import com.instagram.api.reply.dto.response.ReplyResponse;
import com.instagram.api.reply.service.ReplyService;
import com.instagram.api.user.state.UserAuthorize;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reply")
@UserAuthorize
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping()
    public ResponseEntity<Void> createReply(@AuthenticationPrincipal User user, @RequestBody ReplyCreateRequest replyCreateRequest) {
        replyService.createReply(UUID.fromString(user.getUsername()), replyCreateRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/toReply")
    public ResponseEntity<Void> replyToReply(@AuthenticationPrincipal User user, @RequestBody ReplyToReplyRequest replyToReplyRequest) {
        Reply reply = replyService.checkExist(replyToReplyRequest.getParent_id());
        replyService.replyToReply(UUID.fromString(user.getUsername()), replyToReplyRequest, reply);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<List<ReplyResponse>> retrieveReplyByPost(@AuthenticationPrincipal User user, @PathVariable Long post_id) {
        return ResponseEntity.ok(replyService.retrieveReplyByPost(post_id));
    }

    @GetMapping("/{reply_id}")
    public ResponseEntity<ReplyResponse> retrieveReplyById(@AuthenticationPrincipal User user, @PathVariable Long reply_id) {
        return ResponseEntity.ok(replyService.retrieveReplyById(reply_id));
    }

    @PutMapping()
    public ResponseEntity<Void> updateReply(@AuthenticationPrincipal User user, @RequestBody ReplyUpdateRequest replyUpdateRequest) {
        replyService.updateReply(UUID.fromString(user.getUsername()), replyUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<Void> deleteReply(@AuthenticationPrincipal User user, @PathVariable Long post_id) {
        replyService.deleteReply(UUID.fromString(user.getUsername()), post_id);
        return ResponseEntity.ok().build();
    }


}
