package com.marketplace.product.service;

import com.marketplace.product.dto.request.CreateProductRequest;
import com.marketplace.product.dto.request.UpdateProductRequest;
import com.marketplace.product.dto.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse getProduct(UUID productId);

    List<ProductResponse> getProductsByShop(UUID shopId);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(
            UUID productId,
            UpdateProductRequest request);

    void deleteProduct(UUID productId);

}
