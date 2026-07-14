package com.marketplace.shop.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShopRequest {

    private String shopName;

    private String description;

    private String phone;

    private String email;

    private String address;

    private String logoUrl;

    private String bannerUrl;
}
