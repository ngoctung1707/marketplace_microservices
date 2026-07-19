package com.marketplace.order.client.product;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.marketplace.order.client.product.config.ProductFeignConfig;
import com.marketplace.order.client.product.dto.InternalProductResponse;

@FeignClient(name = "product-service", path = "/api/v1/internal/products", configuration = ProductFeignConfig.class)
public interface ProductServiceClient {

    @GetMapping("/{productId}")
    InternalProductResponse getProductById(
            @PathVariable("productId") UUID productId);

}