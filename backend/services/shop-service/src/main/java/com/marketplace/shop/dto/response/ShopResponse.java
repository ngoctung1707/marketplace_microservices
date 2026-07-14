package com.marketplace.shop.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopResponse {

    private UUID id;

    private UUID ownerId;

    private String shopName;

    private String description;

    private String logoUrl;

    private String bannerUrl;

    private String phone;

    private String email;

    private String address;

    private boolean verified;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
