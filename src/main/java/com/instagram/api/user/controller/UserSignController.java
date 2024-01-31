package com.instagram.api.user.controller;

import com.instagram.api.user.dto.request.UserLoginRequest;
import com.instagram.api.user.dto.request.UserRegistRequest;
import com.instagram.api.user.dto.response.UserLoginResponse;
import com.instagram.api.user.dto.response.UserResponse;
import com.instagram.api.user.service.UserSignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Tag(name = "USER", description = "회원가입 및 로그인 API")
@EnableWebMvc
@RestController
@RequiredArgsConstructor
public class UserSignController {

    private final UserSignService userSignService;

    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "200", description = "회원가입 성공")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegistRequest userRegistRequest) {
        return ResponseEntity.ok(userSignService.register(userRegistRequest));
    }

    @Operation(summary = "로그인")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "로그인 성공")
            , @ApiResponse(responseCode = "400", description = "로그인 실패")})
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(userSignService.login(userLoginRequest));
    }

}
