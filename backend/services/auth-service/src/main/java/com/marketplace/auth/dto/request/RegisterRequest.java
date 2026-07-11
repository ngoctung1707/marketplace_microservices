package com.marketplace.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String fullName;
}
