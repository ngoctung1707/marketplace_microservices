package com.marketplace.user.dto.response;

import com.marketplace.user.entity.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserProfileResponse {

    private UUID id;

    private UUID authUserId;

    private String email;

    private String fullName;

    private String avatarUrl;

    private String phone;

    private LocalDate birthday;

    private Gender gender;

    private String bio;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}