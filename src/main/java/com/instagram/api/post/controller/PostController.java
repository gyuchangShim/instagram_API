package com.instagram.api.post.controller;

import com.instagram.api.post.dto.request.PostCreateRequest;
import com.instagram.api.post.dto.response.PostResponse;
import com.instagram.api.post.service.PostService;
import com.instagram.api.user.state.UserAuthorize;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    @PostMapping()
    public ResponseEntity<Void> createPost(@AuthenticationPrincipal User user, @RequestBody PostCreateRequest postCreateRequest) {
        postService.createPost(UUID.fromString(user.getUsername()), postCreateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> retrievePostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.retrievePostById(id));
    }

    @GetMapping()
    public ResponseEntity<List<PostResponse>> retrievePostByFollow(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.retrievePostByFollow(UUID.fromString(user.getUsername())));
    }


}
