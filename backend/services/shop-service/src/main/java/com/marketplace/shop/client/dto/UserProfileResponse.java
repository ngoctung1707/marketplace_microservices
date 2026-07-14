package com.marketplace.shop.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private UUID id;

    private UUID authUserId;

    private String email;

    private String fullName;

    private String phone;

    private String avatarUrl;

    private String bio;

    private LocalDate birthday;

    private String gender;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
