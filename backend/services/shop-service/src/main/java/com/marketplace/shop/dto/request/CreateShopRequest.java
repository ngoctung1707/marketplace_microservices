package com.marketplace.shop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShopRequest {

    @NotNull
    private UUID ownerId;

    @NotBlank
    private String shopName;

    private String description;

    private String phone;

    private String email;

    private String address;
}
