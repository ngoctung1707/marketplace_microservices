package com.marketplace.payment.client.order.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
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
