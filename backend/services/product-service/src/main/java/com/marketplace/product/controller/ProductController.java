package com.marketplace.product.controller;

import com.marketplace.product.dto.request.CreateProductRequest;
import com.marketplace.product.dto.request.UpdateProductRequest;
import com.marketplace.product.dto.response.ProductResponse;
import com.marketplace.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponse createProduct(
            @Valid @RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping("/{productId}")
    public ProductResponse getProduct(
            @PathVariable("productId") UUID productId) {
        return productService.getProduct(productId);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/shop/{shopId}")
    public List<ProductResponse> getProductsByShop(
            @PathVariable("shopId") UUID shopId) {
        return productService.getProductsByShop(shopId);
    }

    @PutMapping("/{productId}")
    public ProductResponse updateProduct(
            @PathVariable("productId") UUID productId,
            @Valid @RequestBody UpdateProductRequest request) {
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(
            @PathVariable("productId") UUID productId) {
        productService.deleteProduct(productId);
    }

}
