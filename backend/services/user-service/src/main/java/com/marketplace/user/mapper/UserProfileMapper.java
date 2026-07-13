package com.marketplace.user.mapper;

import com.marketplace.user.dto.request.UpdateProfileRequest;
import com.marketplace.user.dto.response.UserProfileResponse;
import com.marketplace.user.entity.UserProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfileResponse toResponse(UserProfile userProfile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(
            UpdateProfileRequest request,
            @MappingTarget UserProfile entity
    );
}