package com.instagram.api.post.service;

import com.instagram.api.post.domain.Post;
import com.instagram.api.post.dto.request.PostCreateRequest;
import com.instagram.api.post.dto.request.PostUpdateRequest;
import com.instagram.api.post.dto.response.PostResponse;
import com.instagram.api.post.repository.PostRepository;
import com.instagram.api.reply.repository.ReplyRepository;
import com.instagram.api.reply.service.ReplyService;
import com.instagram.api.user.domain.User;
import com.instagram.api.user.repository.UserRepository;
import com.instagram.api.user.service.UserService;
import com.instagram.api.util.S3UploadService;
import com.instagram.api.util.exception.errorCode.PostErrorCode;
import com.instagram.api.util.exception.errorCode.UserErrorCode;
import com.instagram.api.util.exception.exception.CustomException;
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

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
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
        User targetUser = userRepository.getReferenceById(id);
        postRepository.save(postCreateRequest.toEntity(targetUser));
    }

    public PostResponse retrievePostById(Long id) {
        return PostResponse.from(checkExist(id));
    }

    public List<PostResponse> retrievePostByFollow(UUID id) {
        User targetUser = userRepository.getReferenceById(id);
        List<User> followingList = targetUser.getFollowing();
        return postRepository.findAll()
                .stream().filter(post -> followingList.contains(post.getUser()))
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePost(UUID id, PostUpdateRequest postUpdateRequest) {
        Post targetPost = checkExist(postUpdateRequest.getId());
        checkProfile(targetPost, id);
        targetPost.updatePost(postUpdateRequest.getContent());
    }

    @Transactional
    public void deletePost(UUID id, Long post_id) {
        Post targetPost = checkExist(post_id);
        checkProfile(targetPost, id);
        // 게시글 삭제 시 해당 게시글의 모든 댓글 제거(제거할때만 repo에서 바로 제거)
        replyRepository.deleteAllByPost(targetPost);
        postRepository.delete(targetPost);
    }

    // 사용자 탈퇴 시 해당 사용자의 모든 게시글 제거
    @Transactional
    public void deleteAllByUser(UUID id, User targetUser) {
        List<Post> postList = postRepository.findAllByUser(targetUser);
        for(Post post : postList) {
            deletePost(id, post.getId());
        }
    }

    public void checkProfile(Post post, UUID id) {
        User writer = userRepository.getReferenceById(id);
        if(!post.getUser().equals(writer)) {
             throw new CustomException(UserErrorCode.ERROR_USER_PROFILE);
        }
    }

    public Post checkExist(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
    }

}
