package com.marketplace.payment.client.order.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.marketplace.payment.client.order.dto.OrderStatus;
import com.marketplace.payment.client.order.dto.PaymentStatus;

import lombok.Data;

@Data
public class OrderResponse {

    private UUID id;
    private UUID userId;
    private String orderCode;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private String receiverName;
    private String receiverPhone;
    private String shippingAddress;
    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
