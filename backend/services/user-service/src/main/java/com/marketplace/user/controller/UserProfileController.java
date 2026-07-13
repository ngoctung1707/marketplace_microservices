package com.marketplace.user.controller;

import com.marketplace.user.dto.request.CreateUserProfileRequest;
import com.marketplace.user.dto.request.UpdateProfileRequest;
import com.marketplace.user.dto.response.UserProfileResponse;
import com.marketplace.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public UserProfileResponse createProfile(
            @RequestBody CreateUserProfileRequest request
    ) {

        return userProfileService.createProfile(request);
    }

@GetMapping("/{authUserId}")
public UserProfileResponse getProfile(
        @PathVariable("authUserId") UUID authUserId
) {
    return userProfileService.getProfile(authUserId);
}

@PutMapping("/{authUserId}")
public UserProfileResponse updateProfile(
        @PathVariable("authUserId") UUID authUserId,
        @RequestBody UpdateProfileRequest request
) {
    return userProfileService.updateProfile(authUserId, request);
}
}