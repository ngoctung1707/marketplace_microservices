package com.marketplace.payment.client.order;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.marketplace.payment.client.order.dto.request.UpdatePaymentStatusRequest;

@FeignClient(contextId = "internalOrderServiceClient", name = "order-service", path = "/api/v1/internal/orders")
public interface InternalOrderServiceClient {

    @PutMapping("/{orderId}/payment-status")
    void updatePaymentStatus(

            @PathVariable("orderId") UUID orderId,

            @RequestBody UpdatePaymentStatusRequest request);

}
