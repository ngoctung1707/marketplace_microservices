package com.marketplace.category.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.category.dto.request.CreateCategoryRequest;
import com.marketplace.category.dto.request.UpdateCategoryRequest;
import com.marketplace.category.dto.response.CategoryResponse;
import com.marketplace.category.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(
            @Valid @RequestBody CreateCategoryRequest request) {

        return service.create(request);
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategory(
            @PathVariable("id") UUID id) {

        return service.getById(id);
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {

        return service.getAll();
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateCategoryRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(
            @PathVariable("id") UUID id) {

        service.delete(id);
    }
}
