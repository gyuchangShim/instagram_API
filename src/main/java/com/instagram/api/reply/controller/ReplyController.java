package com.instagram.api.reply.controller;

import com.instagram.api.reply.domain.Reply;
import com.instagram.api.reply.dto.request.ReplyCreateRequest;
import com.instagram.api.reply.dto.request.ReplyToReplyRequest;
import com.instagram.api.reply.dto.request.ReplyUpdateRequest;
import com.instagram.api.reply.dto.response.ReplyResponse;
import com.instagram.api.reply.service.ReplyService;
import com.instagram.api.user.state.UserAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "REPLY_FUNCTION", description = "댓글 기능(생성, 조회, 수정, 삭제, 답글) API")
@RestController
@RequestMapping("/reply")
@UserAuthorize
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @Operation(summary = "댓글 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "접근 권한이 없습니다.")
    })
    @PostMapping()
    public ResponseEntity<Void> createReply(@AuthenticationPrincipal User user, @RequestBody ReplyCreateRequest replyCreateRequest) {
        replyService.createReply(UUID.fromString(user.getUsername()), replyCreateRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "답글 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "답글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "해당 댓글이 존재하지 않습니다.")
    })
    @PostMapping("/toReply")
    public ResponseEntity<Void> replyToReply(@AuthenticationPrincipal User user, @RequestBody ReplyToReplyRequest replyToReplyRequest) {
        Reply reply = replyService.checkExist(replyToReplyRequest.getParent_id());
        replyService.replyToReply(UUID.fromString(user.getUsername()), replyToReplyRequest, reply);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글별 댓글 조회")
    @ApiResponse(responseCode = "200", description = "게시글별 댓글 조회 성공")
    @GetMapping("/{post_id}")
    public ResponseEntity<List<ReplyResponse>> retrieveReplyByPost(@AuthenticationPrincipal User user,
                                                                   @Parameter(description = "댓글 조회를 위한 게시글 ID")
                                                                   @PathVariable Long post_id) {
        return ResponseEntity.ok(replyService.retrieveReplyByPost(post_id));
    }

    @Operation(summary = "댓글 단일 조회")
    @ApiResponse(responseCode = "200", description = "댓글 단일 조회 성공")
    @GetMapping("/{reply_id}")
    public ResponseEntity<ReplyResponse> retrieveReplyById(@AuthenticationPrincipal User user,
                                                           @Parameter(description = "조회하고자 하는 댓글 ID")
                                                           @PathVariable Long reply_id) {
        return ResponseEntity.ok(replyService.retrieveReplyById(reply_id));
    }

    @Operation(summary = "댓글 수정")
    @ApiResponse(responseCode = "200", description = "댓글 수정 성공")
    @PutMapping()
    public ResponseEntity<Void> updateReply(@AuthenticationPrincipal User user, @RequestBody ReplyUpdateRequest replyUpdateRequest) {
        replyService.updateReply(UUID.fromString(user.getUsername()), replyUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 삭제")
    @ApiResponse(responseCode = "200", description = "댓글 삭제 성공")
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Void> deleteReply(@AuthenticationPrincipal User user,
                                            @Parameter(description = "삭제하고자 하는 댓글 ID")
                                            @PathVariable Long post_id) {
        replyService.deleteReply(UUID.fromString(user.getUsername()), post_id);
        return ResponseEntity.ok().build();
    }


}
