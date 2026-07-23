package com.marketplace.payment.client.order;

import java.util.UUID;

import com.marketplace.payment.client.order.dto.request.UpdatePaymentStatusRequest;
import com.marketplace.payment.client.order.dto.response.OrderResponse;

public interface OrderClientService {

    OrderResponse getOrder(UUID orderId);

    void updatePaymentStatus(
            UUID orderId,
            UpdatePaymentStatusRequest request);

}
