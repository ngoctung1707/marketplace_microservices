package com.marketplace.payment.client.order;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.marketplace.payment.client.order.dto.request.UpdatePaymentStatusRequest;
import com.marketplace.payment.client.order.dto.response.OrderResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderClientServiceImpl implements OrderClientService {

    private final OrderServiceClient client;
    private final InternalOrderServiceClient internalOrderServiceClient;

    @Override
    public OrderResponse getOrder(UUID orderId) {

        return client.getOrder(orderId);

    }

    @Override
    public void updatePaymentStatus(
            UUID orderId,
            UpdatePaymentStatusRequest request) {

        internalOrderServiceClient.updatePaymentStatus(
                orderId,
                request);

    }
}