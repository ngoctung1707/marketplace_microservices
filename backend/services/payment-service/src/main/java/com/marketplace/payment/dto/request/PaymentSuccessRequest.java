package com.marketplace.payment.dto.request;

import lombok.Data;

@Data
public class PaymentSuccessRequest {

    private String transactionId;

    private String gatewayResponse;

}