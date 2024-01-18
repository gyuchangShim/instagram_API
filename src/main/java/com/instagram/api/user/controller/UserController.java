package com.instagram.api.user.controller;

import com.instagram.api.user.dto.request.UserLoginRequest;
import com.instagram.api.user.dto.request.UserRegistRequest;
import com.instagram.api.user.dto.request.UserUpdateRequest;
import com.instagram.api.user.dto.response.UserLoginResponse;
import com.instagram.api.user.dto.response.UserResponse;
import com.instagram.api.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // TODO DTO + MultipartFile은 Swagger에서 확인 불가 -> Postman으로 임시 조치(첫번째 피드백에서 질문 예정)
//    @PostMapping()
//    public ResponseEntity<Void> register(@RequestPart UserRegistRequest userRegistRequest,
//                                         @RequestPart MultipartFile multipartFile) throws IOException {
//        userService.register(userRegistRequest, multipartFile);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegistRequest userRegistRequest) {
        userService.register(userRegistRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(userService.login(userLoginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> retrieveUsers() {
        return ResponseEntity.ok(userService.retrieveUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> retrieveUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.retrieveUserById(id));
    }

    @PutMapping("")
    public ResponseEntity<Void> updateUser(@Valid @RequestPart UserUpdateRequest userUpdateRequest,
                                           @RequestPart(required = false) MultipartFile multipartFile) throws IOException {
        userService.updateUser(userUpdateRequest, multipartFile);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }



}
