package com.marketplace.product.client.shop;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class ShopDto {

    private UUID id;

    private UUID ownerId;

    private String shopName;

    private String description;

    private String logoUrl;

    private String bannerUrl;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
