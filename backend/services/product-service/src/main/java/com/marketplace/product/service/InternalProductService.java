package com.marketplace.product.service;

import java.util.UUID;

import com.marketplace.product.dto.internal.InternalProductResponse;

public interface InternalProductService {

    InternalProductResponse getProductById(UUID productId);

}
