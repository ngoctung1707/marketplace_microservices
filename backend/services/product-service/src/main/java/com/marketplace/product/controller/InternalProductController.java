package com.marketplace.product.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.product.dto.internal.InternalProductResponse;
import com.marketplace.product.service.InternalProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/internal/products")
@RequiredArgsConstructor
public class InternalProductController {

    private final InternalProductService internalProductService;

    @GetMapping("/{id}")
    public InternalProductResponse getProductById(
            @PathVariable("id") UUID id) {

        return internalProductService.getProductById(id);
    }
}
