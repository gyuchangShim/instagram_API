package com.instagram.api.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String followerName;
    private String followingName;
    @CreationTimestamp
    private Timestamp timestamp;

    @Builder
    public Follow(String followerName, String followingName) {
        this.followerName = followerName;
        this.followingName = followingName;
    }

}
