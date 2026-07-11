package com.marketplace.auth.dto.response;

import java.util.UUID;
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
public class RegisterResponse {

    private UUID id;
    private String email;
    private String fullName;
    private String message;
}
