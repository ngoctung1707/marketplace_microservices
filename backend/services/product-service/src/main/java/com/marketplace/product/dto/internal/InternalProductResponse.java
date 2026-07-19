package com.marketplace.product.dto.internal;

import java.math.BigDecimal;
import java.util.UUID;

import com.marketplace.product.entity.ProductStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InternalProductResponse {

    private UUID id;

    private UUID shopId;

    private UUID categoryId;

    private UUID brandId;

    private String name;

    private String thumbnail;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private ProductStatus status;

    private Boolean active;

}
