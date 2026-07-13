package com.marketplace.user.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserProfileRequest {

    private UUID authUserId;

    private String email;

    private String fullName;
}