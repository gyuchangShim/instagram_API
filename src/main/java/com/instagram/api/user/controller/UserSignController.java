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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

@Tag(name = "USER", description = "회원가입 및 로그인 API")
@EnableWebMvc
@RestController
@RequiredArgsConstructor
public class UserSignController {

    private final UserSignService userSignService;

    // TODO DTO + MultipartFile은 Swagger에서 확인 불가 -> Postman으로 임시 조치(첫번째 피드백에서 질문 예정)
    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "200", description = "회원가입 성공")
    @PostMapping()
    public ResponseEntity<Void> register(@RequestPart UserRegistRequest userRegistRequest,
                                         @RequestPart MultipartFile multipartFile) throws IOException {
        userSignService.register(userRegistRequest, multipartFile);
        return ResponseEntity.ok().build();
    }

//    @Operation(summary = "회원가입")
//    @ApiResponse(responseCode = "200", description = "회원가입 성공")
//    @PostMapping("/register")
//    public ResponseEntity<UserResponse> register(@RequestBody UserRegistRequest userRegistRequest) {
//        return ResponseEntity.ok(userSignService.register(userRegistRequest));
//    }

    @Operation(summary = "로그인")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "로그인 성공")
            , @ApiResponse(responseCode = "400", description = "로그인 실패")})
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(userSignService.login(userLoginRequest));
    }

}
