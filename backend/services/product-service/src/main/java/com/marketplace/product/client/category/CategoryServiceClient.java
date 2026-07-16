package com.marketplace.product.client.category;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service", path = "/api/v1/categories")
public interface CategoryServiceClient {

    @GetMapping("/{id}")
    CategoryDto getCategoryById(
            @PathVariable("id") UUID id);
}
