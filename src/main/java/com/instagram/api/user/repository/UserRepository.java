package com.instagram.api.user.repository;

import com.instagram.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUid(String id);

    Optional<User> findByName(String name);
}
