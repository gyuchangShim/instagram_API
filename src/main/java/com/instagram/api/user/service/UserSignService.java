package com.instagram.api.user.service;

import com.instagram.api.user.dto.response.UserResponse;
import com.instagram.api.util.exception.exception.CustomException;
import com.instagram.api.util.exception.errorCode.UserErrorCode;
import com.instagram.api.util.jwt.TokenProvider;
import com.instagram.api.user.domain.User;
import com.instagram.api.user.dto.request.UserLoginRequest;
import com.instagram.api.user.dto.request.UserRegistRequest;
import com.instagram.api.user.dto.response.UserLoginResponse;
import com.instagram.api.user.repository.UserRepository;
import com.instagram.api.util.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSignService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public UserResponse register(UserRegistRequest userRegistRequest) {
        String encodePw = encoder.encode(userRegistRequest.getPw());
        checkIdDuplicate(userRegistRequest);
        User user = userRegistRequest.toEntity(null, null, encodePw);
        userRepository.save(user);
        return new UserResponse(user.getUid(), user.getPw(), user.getName(), user.getAge(), user.getPhoneNumber(), user.getRole());
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User targetUser = userRepository.findByUid(userLoginRequest.getUid())
                .filter(user -> encoder.matches(userLoginRequest.getPw(), user.getPw()))
                .orElseThrow(() -> new CustomException(UserErrorCode.PASSWORD_NOT_MATCH));
        String token = tokenProvider.createToken(String.format("%s:%s", targetUser.getId(), targetUser.getRole()));
        return new UserLoginResponse(targetUser.getName(), targetUser.getRole(), token);
    }

    private void checkIdDuplicate(UserRegistRequest userRegistRequest) {
        if(userRepository.findByUid(userRegistRequest.getUid()).isPresent()) {
            throw new CustomException(UserErrorCode.DUPLICATE_ID);
        }
    }

}
