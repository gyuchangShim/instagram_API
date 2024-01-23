package com.instagram.api.post.service;

import com.instagram.api.post.domain.Post;
import com.instagram.api.post.dto.request.PostCreateRequest;
import com.instagram.api.post.dto.request.PostUpdateRequest;
import com.instagram.api.post.dto.response.PostResponse;
import com.instagram.api.post.repository.PostRepository;
import com.instagram.api.user.domain.User;
import com.instagram.api.user.service.UserService;
import com.instagram.api.util.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final S3UploadService s3UploadService;

    // TODO 이미지 저장 가능
//    @Transactional
//    public void createPost(UUID id, PostCreateRequest postCreateRequest) throws IOException {
//        User targetUser = userService.checkExist(id);
//        String image = s3UploadService.saveFile(postCreateRequest.getImage());
//        postRepository.save(postCreateRequest.toEntity(targetUser, image));
//    }

    @Transactional
    public void createPost(UUID id, PostCreateRequest postCreateRequest) {
        User targetUser = userService.checkExist(id);
        postRepository.save(postCreateRequest.toEntity(targetUser));
    }

    public PostResponse retrievePostById(Long id) {
        return PostResponse.from(checkExist(id));
    }

    public List<PostResponse> retrievePostByFollow(UUID id) {
        User targetUser = userService.checkExist(id);
        List<User> followingList = targetUser.getFollowing();
        return postRepository.findAll()
                .stream().filter(post -> followingList.contains(post.getUser()))
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePost(UUID fromString, PostUpdateRequest postUpdateRequest) {
        Post targetPost = checkExist(postUpdateRequest.getId());
        targetPost.updatePost(postUpdateRequest.getContent());
    }

    public Post checkExist(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 게시물이 존재하지 않습니다."));
    }


    public void deletePost(UUID fromString, Long id) {
        Post targetPost = checkExist(id);
        postRepository.delete(targetPost);
    }
}
