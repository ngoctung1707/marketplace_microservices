package com.marketplace.order.service;

import java.util.List;
import java.util.UUID;

import com.marketplace.order.dto.internal.UpdatePaymentStatusRequest;
import com.marketplace.order.dto.request.CreateOrderRequest;
import com.marketplace.order.dto.response.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse getOrder(UUID orderId);

    List<OrderResponse> getOrdersByUser(UUID userId);

    List<OrderResponse> getAllOrders();

    void cancelOrder(UUID orderId);

    void updatePaymentStatus(UUID orderId, UpdatePaymentStatusRequest request);

}
