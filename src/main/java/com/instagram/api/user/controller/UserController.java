package com.instagram.api.user.controller;

import com.instagram.api.user.dto.request.UserUpdateRequest;
import com.instagram.api.user.dto.response.UserResponse;
import com.instagram.api.user.service.UserService;
import com.instagram.api.user.state.UserAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Tag(name = "USER_FUNCTION", description = "회원 기능(정보 조회, 수정, 탈퇴, 팔로우) API")
@RequestMapping("/user")
@RestController
@UserAuthorize
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 전체 조회")
    @ApiResponse(responseCode = "200", description = "사용자 전체 조회 성공")
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> retrieveUsers() {
        return ResponseEntity.ok(userService.retrieveUsers());
    }

    @Operation(summary = "회원 정보 단일 조회")
    @ApiResponse(responseCode = "200", description = "사용자 단일 정보 조회 성공")
    @GetMapping()
    public ResponseEntity<UserResponse> retrieveUserByName(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.retrieveUserByName(UUID.fromString(user.getUsername())));
    }

    @Operation(summary = "회원 프로필 사진 수정")
    @ApiResponse(responseCode = "200", description = "사용자 프로필 사진 업데이트 성공")
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> insertProfileImage(@AuthenticationPrincipal User user,
                                                   @RequestPart MultipartFile multipartFile) throws IOException {
        userService.insertProfileImage(UUID.fromString(user.getUsername()), multipartFile);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원 정보 수정")
    @ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공")
    @PutMapping()
    public ResponseEntity<Void> updateUser(@AuthenticationPrincipal User user,
                                           @Valid @RequestPart UserUpdateRequest userUpdateRequest) {
        userService.updateUser(UUID.fromString(user.getUsername()), userUpdateRequest);
        return ResponseEntity.ok().build();
    }

    /*
    // TODO Postman으로 기능 확인
    @PutMapping()
    public ResponseEntity<Void> updateUser(@AuthenticationPrincipal User user,
                                           @Valid @RequestPart UserUpdateRequest userUpdateRequest,
                                           @RequestPart(required = false) MultipartFile multipartFile) throws IOException {
        userService.updateUser(UUID.fromString(user.getUsername()), userUpdateRequest, multipartFile);
        return ResponseEntity.ok().build();
    } */

    @Operation(summary = "회원 탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "접근 권한이 없습니다.")
    })
    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(UUID.fromString(user.getUsername()));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "팔로우")
    @ApiResponse(responseCode = "200", description = "팔로우 성공")
    @PostMapping("/follow/{name}")
    public ResponseEntity<Void> follow(@AuthenticationPrincipal User user,
                                       @Parameter(description = "팔로우 대상 이름") @PathVariable String name) {
        userService.follow(UUID.fromString(user.getUsername()), name);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "언팔로우")
    @ApiResponse(responseCode = "200", description = "언팔로우 성공")
    @DeleteMapping("/follow/{name}")
    public ResponseEntity<Void> unFollow(@AuthenticationPrincipal User user,
                                         @Parameter(description = "언팔로우 대상 이름") @PathVariable String name) {
        userService.unFollow(UUID.fromString(user.getUsername()), name);
        return ResponseEntity.ok().build();
    }

}
