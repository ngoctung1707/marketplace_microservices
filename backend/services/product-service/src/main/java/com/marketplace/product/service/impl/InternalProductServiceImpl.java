package com.marketplace.product.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.product.dto.internal.InternalProductResponse;
import com.marketplace.product.entity.Product;
import com.marketplace.product.exception.ProductNotFoundException;
import com.marketplace.product.mapper.InternalProductMapper;
import com.marketplace.product.repository.ProductRepository;
import com.marketplace.product.service.InternalProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InternalProductServiceImpl implements InternalProductService {

    private final ProductRepository repository;

    private final InternalProductMapper mapper;

    @Override
    public InternalProductResponse getProductById(UUID productId) {

        Product product = repository.findByIdAndActiveTrue(productId)
                .orElseThrow(ProductNotFoundException::new);

        return mapper.toInternalResponse(product);
    }

}
