package com.marketplace.payment.client.order.dto.request;

import com.marketplace.payment.client.order.dto.OrderStatus;
import com.marketplace.payment.client.order.dto.PaymentStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePaymentStatusRequest {

    @NotNull
    private PaymentStatus paymentStatus;

    @NotNull
    private OrderStatus orderStatus;
}
