package com.marketplace.product.dto.request;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class UpdateProductRequest {

    private UUID categoryId;

    private UUID brandId;

    private String name;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    private BigDecimal discountPrice;

    private String thumbnail;
}
