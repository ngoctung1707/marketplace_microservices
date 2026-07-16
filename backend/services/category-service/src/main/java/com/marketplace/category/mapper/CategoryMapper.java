package com.marketplace.category.mapper;

import org.mapstruct.Mapper;

import com.marketplace.category.dto.request.CreateCategoryRequest;
import com.marketplace.category.dto.response.CategoryResponse;
import com.marketplace.category.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CreateCategoryRequest request);

    CategoryResponse toResponse(Category category);
}