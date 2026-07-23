package com.marketplace.payment.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.marketplace.payment.entity.PaymentMethod;
import com.marketplace.payment.entity.PaymentStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private UUID id;

    private UUID orderId;

    private String paymentCode;

    private PaymentMethod paymentMethod;

    private PaymentStatus status;

    private BigDecimal amount;

    private String transactionId;

    private String gatewayResponse;

    private LocalDateTime paidAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
