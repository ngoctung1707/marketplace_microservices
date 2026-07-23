package com.marketplace.payment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentFailedRequest {

    @NotBlank
    private String gatewayResponse;

}
