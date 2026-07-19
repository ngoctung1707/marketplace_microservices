
package com.marketplace.order.client.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
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
