package com.instagram.api.user.service;

import com.instagram.api.post.domain.Post;
import com.instagram.api.post.repository.PostRepository;
import com.instagram.api.post.service.PostService;
import com.instagram.api.reply.dto.response.ReplyResponse;
import com.instagram.api.reply.repository.ReplyRepository;
import com.instagram.api.reply.service.ReplyService;
import com.instagram.api.user.domain.Follow;
import com.instagram.api.user.domain.User;
import com.instagram.api.user.dto.request.UserUpdateRequest;
import com.instagram.api.user.dto.response.UserResponse;
import com.instagram.api.user.repository.FollowRepository;
import com.instagram.api.user.repository.UserRepository;
import com.instagram.api.util.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PostService postService;
    private final S3UploadService s3UploadService;
    private final PasswordEncoder encoder;

    public UserResponse retrieveUserByName(UUID id) {
        return UserResponse.from(checkExist(id));
    }

    public List<UserResponse> retrieveUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public UserResponse updateUser(UUID id, UserUpdateRequest userUpdateRequest, MultipartFile multipartFile) throws IOException {
        return userRepository.findById(id)
                .filter(user -> encoder.matches(userUpdateRequest.getPw(), user.getPw()))
                .map(user -> {
                    user.updateUser(userUpdateRequest.getName(), userUpdateRequest.getAge(), userUpdateRequest.getPhoneNumber());
                    return UserResponse.from(user);
                })
                .orElseThrow(() -> new NoSuchElementException("아이디 또는 비밀번호가 일치하지 않습니다."));
//        if(targetUser.getFileName() != multipartFile.getOriginalFilename()) {
//            s3UploadService.deleteImage(targetUser.getFileName());
//            String imageUrl = s3UploadService.saveFile(multipartFile);
//            targetUser.updateImage(imageUrl, multipartFile.getOriginalFilename());
//        }
    }

    @Transactional
    public void deleteUser(UUID id) {
        User targetUser = checkExist(id);
//        s3UploadService.deleteImage(targetUser.getFileName());
//      사용자 제거 시 해당 사용자의 게시글, 댓글, 팔로우 제거(제거할때만 service를 통해 제거)
        postService.deleteAllByUser(id, targetUser);
        followRepository.deleteAllByFollowerName(targetUser.getName());
        userRepository.delete(checkExist(id));
    }

    @Transactional
    public void follow(UUID fromString, String name) {
        User me = checkExist(fromString);
        User friend = findUser(name);
        if(me == friend) {
            throw new IllegalArgumentException("자기 자신은 follow할 수 없습니다.");
        } else if(followRepository.findFollow(me.getName(), name).isPresent()) {
            throw new IllegalArgumentException("이미 팔로우 한 대상입니다.");
        }
        me.updateFollow(friend);
        friend.updateFollower(me);
        followRepository.save(new Follow(me.getName(), friend.getName()));
    }

    @Transactional
    public void unFollow(UUID id, String name) {
        User me = checkExist(id);
        User friend = findUser(name);
        if(followRepository.findFollow(me.getName(), name).isPresent()) {
            me.unFollow(friend);
            friend.unFollower(me);
            followRepository.deleteByFollowingName(name);
        }
    }

    public User checkExist(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 계정입니다."));
    }

    public User findUser(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 계정입니다."));
    }


}
