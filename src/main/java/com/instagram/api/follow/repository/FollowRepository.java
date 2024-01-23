package com.instagram.api.follow.repository;

import com.instagram.api.follow.domain.Follow;
import com.instagram.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Query("select f from Follow f where f.follower = :from and f.following = :to")
    Optional<Follow> findFollow(@Param("from") User mine, @Param("to")User friend);

    void deleteByFollowing(User targetUser);
}
