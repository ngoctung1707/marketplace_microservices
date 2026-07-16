package com.marketplace.product.mapper;

import com.marketplace.product.dto.request.CreateProductRequest;
import com.marketplace.product.dto.request.UpdateProductRequest;
import com.marketplace.product.dto.response.ProductResponse;
import com.marketplace.product.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(CreateProductRequest request);

    ProductResponse toResponse(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(
            UpdateProductRequest request,
            @MappingTarget Product product);

}
