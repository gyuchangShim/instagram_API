package com.instagram.api.user.controller;

import com.instagram.api.user.dto.request.UserUpdateRequest;
import com.instagram.api.user.dto.response.UserResponse;
import com.instagram.api.user.service.UserService;
import com.instagram.api.user.state.UserAuthorize;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/user")
@RestController
@UserAuthorize
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> retrieveUsers() {
        return ResponseEntity.ok(userService.retrieveUsers());
    }

    @GetMapping()
    public ResponseEntity<UserResponse> retrieveUserByName(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.retrieveUserByName(UUID.fromString(user.getUsername())));
    }

    @PutMapping()
    public ResponseEntity<Void> updateUser(@AuthenticationPrincipal User user,
                                           @Valid @RequestPart UserUpdateRequest userUpdateRequest,
                                           @RequestPart(required = false) MultipartFile multipartFile) throws IOException {
        userService.updateUser(UUID.fromString(user.getUsername()), userUpdateRequest, multipartFile);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(UUID.fromString(user.getUsername()));
        return ResponseEntity.ok().build();
    }

}
