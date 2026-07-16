package com.marketplace.product.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.marketplace.product.entity.ProductStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

    private UUID id;

    private UUID shopId;

    private UUID categoryId;

    private String shopName;

    private String categoryName;

    private UUID brandId;

    private String name;

    private String slug;

    private String description;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private String thumbnail;

    private Double ratingAverage;

    private Long soldCount;

    private Long viewCount;

    private ProductStatus status;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
