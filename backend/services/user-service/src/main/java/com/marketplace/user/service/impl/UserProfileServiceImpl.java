package com.marketplace.user.service.impl;

import com.marketplace.user.dto.request.UpdateProfileRequest;
import com.marketplace.user.dto.response.UserProfileResponse;
import com.marketplace.user.entity.UserProfile;
import com.marketplace.user.exception.UserProfileAlreadyExistsException;
import com.marketplace.user.exception.UserProfileNotFoundException;
import com.marketplace.user.mapper.UserProfileMapper;
import com.marketplace.user.repository.UserProfileRepository;
import com.marketplace.user.service.UserProfileService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository repository;

    private final UserProfileMapper mapper;

    @Override
    public UserProfileResponse createProfile(
            UUID authUserId,
            String email,
            String fullName
    ) {

        if (repository.existsByAuthUserId(authUserId)) {
            throw new UserProfileAlreadyExistsException();
        }

        UserProfile profile = UserProfile.builder()
                .authUserId(authUserId)
                .email(email)
                .fullName(fullName)
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