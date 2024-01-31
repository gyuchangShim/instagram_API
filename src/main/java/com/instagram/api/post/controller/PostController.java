package com.instagram.api.post.controller;

import com.instagram.api.post.dto.request.PostCreateRequest;
import com.instagram.api.post.dto.request.PostUpdateRequest;
import com.instagram.api.post.dto.response.PostResponse;
import com.instagram.api.post.service.PostService;
import com.instagram.api.user.state.UserAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "POST FUNCTION", description = "게시글 기능(생성, 조회, 수정, 삭제) API")
@RequestMapping("/post")
@RestController
@UserAuthorize
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // TODO 이미지 삽입 가능
//    @PostMapping
//    public ResponseEntity<Void> createPost(@AuthenticationPrincipal User user, @RequestBody PostCreateRequest postCreateRequest) throws IOException {
//        postService.createPost(UUID.fromString(user.getUsername()), postCreateRequest);
//        return ResponseEntity.ok().build();
//    }

    @Operation(summary = "게시글 생성")
    @ApiResponse(responseCode = "200", description = "게시글 생성 성공")
    @PostMapping()
    public ResponseEntity<Void> createPost(@AuthenticationPrincipal User user, @RequestBody PostCreateRequest postCreateRequest) {
        postService.createPost(UUID.fromString(user.getUsername()), postCreateRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글 단일 조회")
    @ApiResponse(responseCode = "200", description = "게시글 단일 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> retrievePostById(@Parameter(description = "조회하고자 하는 게시글 ID")
                                                             @PathVariable Long id) {
        return ResponseEntity.ok(postService.retrievePostById(id));
    }

    @Operation(summary = "피드 조회")
    @ApiResponse(responseCode = "200", description = "피드 조회 성공")
    @GetMapping()
    public ResponseEntity<List<PostResponse>> retrievePostByFollow(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.retrievePostByFollow(UUID.fromString(user.getUsername())));
    }

    @Operation(summary = "게시글 수정")
    @ApiResponse(responseCode = "200", description = "게시글 수정 성공")
    @PutMapping()
    public ResponseEntity<Void> updatePost(@AuthenticationPrincipal User user, @RequestBody PostUpdateRequest postUpdateRequest) {
        postService.updatePost(UUID.fromString(user.getUsername()), postUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글 삭제")
    @ApiResponse(responseCode = "200", description = "게시글 삭제 성공")
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Void> deletePost(@AuthenticationPrincipal User user,
                                           @Parameter(description = "삭제하고자 하는 게시글 ID")
                                           @PathVariable Long post_id) {
        postService.deletePost(UUID.fromString(user.getUsername()), post_id);
        return ResponseEntity.ok().build();
    }

}
