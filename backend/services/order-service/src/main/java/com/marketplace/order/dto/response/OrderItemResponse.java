package com.marketplace.order.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {

    private UUID id;

    private UUID productId;

    private UUID shopId;

    private String productName;

    private String thumbnail;

    private BigDecimal unitPrice;

    private Integer quantity;

    private BigDecimal subtotal;

}
