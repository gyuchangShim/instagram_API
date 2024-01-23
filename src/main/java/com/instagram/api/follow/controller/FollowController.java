package com.instagram.api.follow.controller;

import com.instagram.api.follow.service.FollowService;
import com.instagram.api.user.state.UserAuthorize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/follow")
@UserAuthorize
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{friend}")
    public ResponseEntity<Void> follow(@AuthenticationPrincipal User user, @PathVariable("friend") String name) {
        followService.follow(UUID.fromString(user.getUsername()), name);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{friend}")
    public ResponseEntity<Void> unFollow(@AuthenticationPrincipal User user, @PathVariable("friend") String name) {
        followService.unFollow(name);
        return ResponseEntity.ok().build();
    }


}
