package com.marketplace.user.service;

import com.marketplace.user.dto.request.UpdateProfileRequest;
import com.marketplace.user.dto.response.UserProfileResponse;

import java.util.UUID;

public interface UserProfileService {

    UserProfileResponse createProfile(
            UUID authUserId,
            String email,
            String fullName
    );

    UserProfileResponse getProfile(UUID authUserId);

    UserProfileResponse updateProfile(
            UUID authUserId,
            UpdateProfileRequest request
    );
}