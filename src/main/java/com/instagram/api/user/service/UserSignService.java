package com.instagram.api.user.service;

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

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserSignService {

    private final UserRepository userRepository;
    private final S3UploadService s3UploadService;
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder encoder;

//    @Transactional
//    public void register(UserRegistRequest userRegistRequest, MultipartFile multipartFile) throws IOException {
//        String imageUrl = s3UploadService.saveFile(multipartFile);
//        String encodePW = encoder.encode(userRegistRequest.getPw());
//        userRepository.save(userRegistRequest.toEntity(multipartFile.getOriginalFilename(), imageUrl, encodePW));
//    }

    @Transactional
    public void register(UserRegistRequest userRegistRequest) {
        String encodePw = encoder.encode(userRegistRequest.getPw());
        userRepository.save(userRegistRequest.toEntity(encodePw));
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User targetUser = userRepository.findByUid(userLoginRequest.getUid())
                .filter(user -> encoder.matches(userLoginRequest.getPw(), user.getPw()))
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 계정입니다."));
        String token = tokenProvider.createToken(String.format("%s:%s", targetUser.getId(), targetUser.getRole()));
        return new UserLoginResponse(targetUser.getName(), targetUser.getRole(), token);
    }
}
