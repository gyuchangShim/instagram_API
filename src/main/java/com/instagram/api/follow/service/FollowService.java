package com.instagram.api.follow.service;

import com.instagram.api.follow.domain.Follow;
import com.instagram.api.follow.repository.FollowRepository;
import com.instagram.api.user.domain.User;
import com.instagram.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final UserService userService;
    private final FollowRepository followRepository;

    @Transactional
    public void follow(UUID id, String name) {
        User myself = userService.checkExist(id);
        User targetUser = userService.findUser(name);

        checkFollow(myself, targetUser);

        Follow follow = new Follow(myself, targetUser);
        followRepository.save(follow);
    }

    @Transactional
    public void unFollow(String name) {
        User targetUser = userService.findUser(name);
        followRepository.deleteByFollowing(targetUser);
    }

    private void checkFollow(User mine, User friend) {
        if(mine == friend) {
            throw new IllegalArgumentException("자기 자신은 follow할 수 없습니다.");
        } else if(followRepository.findFollow(mine, friend).isPresent()) {
            throw new IllegalArgumentException("이미 팔로우 한 대상입니다.");
        }
    }

}
