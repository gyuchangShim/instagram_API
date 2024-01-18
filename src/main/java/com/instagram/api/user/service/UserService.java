package com.instagram.api.user.service;

import com.instagram.api.jwt.TokenProvider;
import com.instagram.api.user.domain.User;
import com.instagram.api.user.dto.request.UserLoginRequest;
import com.instagram.api.user.dto.request.UserRegistRequest;
import com.instagram.api.user.dto.request.UserUpdateRequest;
import com.instagram.api.user.dto.response.UserLoginResponse;
import com.instagram.api.user.dto.response.UserResponse;
import com.instagram.api.user.repository.UserRepository;
import com.instagram.api.util.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

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
        String token = tokenProvider.createToken(String.format("%s:%s", targetUser.getUid(), targetUser.getRole()));
        return new UserLoginResponse(targetUser.getName(), targetUser.getRole(), token);
    }

    public UserResponse retrieveUserById(Long id) {
        return UserResponse.from(checkExist(id));
    }

    public List<UserResponse> retrieveUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserUpdateRequest userUpdateRequest, MultipartFile multipartFile) throws IOException {
        User targetUser = checkExist(userUpdateRequest.getId());
        targetUser.updateUser(userUpdateRequest.getName(), userUpdateRequest.getAge(), userUpdateRequest.getPhoneNumber());
//        if(targetUser.getFileName() != multipartFile.getOriginalFilename()) {
//            s3UploadService.deleteImage(targetUser.getFileName());
//            String imageUrl = s3UploadService.saveFile(multipartFile);
//            targetUser.updateImage(imageUrl, multipartFile.getOriginalFilename());
//        }
    }

    @Transactional
    public void deleteUser(Long id) {
        User targetUser = checkExist(id);
//        s3UploadService.deleteImage(targetUser.getFileName());
        userRepository.delete(checkExist(id));
    }

    private User checkExist(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 계정입니다."));
    }

}
