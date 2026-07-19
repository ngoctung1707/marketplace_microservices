package com.marketplace.order.client.product;

import java.util.UUID;

import com.marketplace.order.client.product.dto.InternalProductResponse;

public interface ProductClientService {

    InternalProductResponse getProduct(UUID productId);

}
