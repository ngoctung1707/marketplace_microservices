package com.marketplace.category.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.marketplace.category.dto.response.CategoryResponse;

@FeignClient(name = "category-service", path = "/api/v1/categories")
public interface CategoryServiceClient {

    @GetMapping("/{id}")
    CategoryResponse getCategoryById(
            @PathVariable("id") UUID id);

}
