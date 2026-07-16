package com.marketplace.category.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.marketplace.category.dto.request.CreateCategoryRequest;
import com.marketplace.category.dto.request.UpdateCategoryRequest;
import com.marketplace.category.dto.response.CategoryResponse;
import com.marketplace.category.entity.Category;
import com.marketplace.category.exception.CategoryAlreadyExistsException;
import com.marketplace.category.exception.CategoryNotFoundException;
import com.marketplace.category.mapper.CategoryMapper;
import com.marketplace.category.repository.CategoryRepository;
import com.marketplace.category.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public CategoryResponse create(CreateCategoryRequest request) {

        if (repository.existsByName(request.getName())) {
            throw new CategoryAlreadyExistsException(request.getName());
        }

        Category category = mapper.toEntity(request);

        if (category.getSlug() == null || category.getSlug().isBlank()) {
            category.setSlug(
                    request.getName()
                            .toLowerCase()
                            .replace(" ", "-"));
        }

        Category saved = repository.save(category);

        return mapper.toResponse(saved);
    }

    @Override
    public CategoryResponse getById(UUID id) {

        Category category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        return mapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse update(UUID id, UpdateCategoryRequest request) {

        Category category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if (request.getName() != null)
            category.setName(request.getName());

        if (request.getDescription() != null)
            category.setDescription(request.getDescription());

        if (request.getSlug() != null)
            category.setSlug(request.getSlug());

        if (request.getParentId() != null)
            category.setParentId(request.getParentId());

        if (request.getImageUrl() != null)
            category.setImageUrl(request.getImageUrl());

        Category updated = repository.save(category);

        return mapper.toResponse(updated);
    }

    @Override
    public void delete(UUID id) {

        if (!repository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }

        repository.deleteById(id);
    }
}
