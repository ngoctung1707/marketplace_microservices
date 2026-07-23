package com.marketplace.payment.dto.request;

import java.util.UUID;

import com.marketplace.payment.entity.PaymentMethod;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePaymentRequest {
    @NotNull
    private UUID orderId;
    @NotNull
    private PaymentMethod paymentMethod;
}
