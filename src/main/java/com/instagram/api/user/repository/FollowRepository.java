package com.instagram.api.user.repository;

import com.instagram.api.user.domain.Follow;
import com.instagram.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Query("select f from Follow f where f.followerName = :from and f.followingName = :to")
    Optional<Follow> findFollow(@Param("from") String mine, @Param("to") String friend);

    List<User> findByFollowingName(User targetUser);

    void deleteByFollowingName(String name);

    void deleteAllByFollowerName(String name);
}
