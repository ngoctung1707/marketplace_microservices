package com.marketplace.product.mapper;

import org.mapstruct.Mapper;

import com.marketplace.product.dto.internal.InternalProductResponse;
import com.marketplace.product.entity.Product;

@Mapper(componentModel = "spring")
public interface InternalProductMapper {

    InternalProductResponse toInternalResponse(Product product);

}
