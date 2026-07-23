package com.marketplace.payment.client.order;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.marketplace.payment.client.order.dto.response.OrderResponse;

@FeignClient(contextId = "orderServiceClient", name = "order-service", path = "/api/v1/orders")
public interface OrderServiceClient {

    @GetMapping("/{orderId}")
    OrderResponse getOrder(@PathVariable("orderId") UUID orderId);

}
