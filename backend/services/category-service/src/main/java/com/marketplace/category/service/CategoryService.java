package com.marketplace.category.service;

import java.util.List;
import java.util.UUID;

import com.marketplace.category.dto.request.CreateCategoryRequest;
import com.marketplace.category.dto.request.UpdateCategoryRequest;
import com.marketplace.category.dto.response.CategoryResponse;

public interface CategoryService {

    CategoryResponse create(CreateCategoryRequest request);

    CategoryResponse getById(UUID id);

    List<CategoryResponse> getAll();

    CategoryResponse update(UUID id, UpdateCategoryRequest request);

    void delete(UUID id);
}