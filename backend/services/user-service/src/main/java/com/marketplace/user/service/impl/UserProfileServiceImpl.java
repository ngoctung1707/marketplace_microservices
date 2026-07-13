package com.marketplace.user.service.impl;

import com.marketplace.user.dto.request.CreateUserProfileRequest;
import com.marketplace.user.dto.request.UpdateProfileRequest;
import com.marketplace.user.dto.response.UserProfileResponse;
import com.marketplace.user.entity.UserProfile;
import com.marketplace.user.exception.UserProfileAlreadyExistsException;
import com.marketplace.user.exception.UserProfileNotFoundException;
import com.marketplace.user.mapper.UserProfileMapper;
import com.marketplace.user.repository.UserProfileRepository;
import com.marketplace.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository repository;

    private final UserProfileMapper mapper;

    @Override
    public UserProfileResponse createProfile(CreateUserProfileRequest request) {

        if (repository.existsByAuthUserId(request.getAuthUserId())) {
            throw new UserProfileAlreadyExistsException();
        }

        UserProfile profile = UserProfile.builder()
                .authUserId(request.getAuthUserId())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .active(true)
                .build();

        repository.save(profile);

        return mapper.toResponse(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(UUID authUserId) {

        UserProfile profile = repository.findByAuthUserId(authUserId)
                .orElseThrow(UserProfileNotFoundException::new);

        return mapper.toResponse(profile);
    }

    @Override
    public UserProfileResponse updateProfile(
            UUID authUserId,
            UpdateProfileRequest request
    ) {

        UserProfile profile = repository.findByAuthUserId(authUserId)
                .orElseThrow(UserProfileNotFoundException::new);

        mapper.updateEntityFromRequest(request, profile);

        repository.save(profile);

        return mapper.toResponse(profile);
    }

}