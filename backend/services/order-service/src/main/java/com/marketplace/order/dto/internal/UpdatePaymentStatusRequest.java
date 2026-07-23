package com.marketplace.order.dto.internal;

import com.marketplace.order.entity.OrderStatus;
import com.marketplace.order.entity.PaymentStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePaymentStatusRequest {

    @NotNull
    private PaymentStatus paymentStatus;

    @NotNull
    private OrderStatus orderStatus;
}
