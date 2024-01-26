package com.instagram.api.user.controller;

import com.instagram.api.user.dto.request.UserLoginRequest;
import com.instagram.api.user.dto.request.UserRegistRequest;
import com.instagram.api.user.dto.response.UserLoginResponse;
import com.instagram.api.user.dto.response.UserResponse;
import com.instagram.api.user.service.UserService;
import com.instagram.api.user.service.UserSignService;
import com.instagram.api.util.exception.handler.CustomExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserSignController {

    private final UserSignService userSignService;

    // TODO DTO + MultipartFile은 Swagger에서 확인 불가 -> Postman으로 임시 조치(첫번째 피드백에서 질문 예정)
//    @PostMapping()
//    public ResponseEntity<Void> register(@RequestPart UserRegistRequest userRegistRequest,
//                                         @RequestPart MultipartFile multipartFile) throws IOException {
//        userSignService.register(userRegistRequest, multipartFile);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegistRequest userRegistRequest) {
        return ResponseEntity.ok(userSignService.register(userRegistRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(userSignService.login(userLoginRequest));
    }

}
