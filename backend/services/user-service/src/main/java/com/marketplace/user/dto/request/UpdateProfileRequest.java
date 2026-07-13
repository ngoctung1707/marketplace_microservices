package com.marketplace.user.dto.request;

import com.marketplace.user.entity.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfileRequest {

    private String fullName;

    private String phone;

    private String avatarUrl;

    private LocalDate birthday;

    private Gender gender;

    private String bio;
}