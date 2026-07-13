package com.marketplace.user.repository;

import com.marketplace.user.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    Optional<UserProfile> findByAuthUserId(UUID authUserId);

    Optional<UserProfile> findByEmail(String email);

    boolean existsByAuthUserId(UUID authUserId);

    boolean existsByEmail(String email);
}