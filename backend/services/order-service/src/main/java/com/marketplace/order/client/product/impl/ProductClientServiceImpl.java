package com.marketplace.order.client.product.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.marketplace.order.client.product.ProductClientService;
import com.marketplace.order.client.product.ProductServiceClient;
import com.marketplace.order.client.product.dto.InternalProductResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductClientServiceImpl implements ProductClientService {

    private final ProductServiceClient productServiceClient;

    @Override
    public InternalProductResponse getProduct(UUID productId) {

        return productServiceClient.getProductById(productId);

    }

}