package com.marketplace.product.client.shop;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "shop-service")
public interface ShopServiceClient {

    @GetMapping("/api/v1/shops/{shopId}")
    ShopDto getShopById(
            @PathVariable("shopId") UUID shopId);

}
