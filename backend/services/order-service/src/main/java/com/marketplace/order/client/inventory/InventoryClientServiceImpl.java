package com.marketplace.order.client.inventory;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.marketplace.order.client.inventory.dto.ConfirmInventoryRequest;
import com.marketplace.order.client.inventory.dto.ReleaseInventoryRequest;
import com.marketplace.order.client.inventory.dto.ReserveInventoryRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryClientServiceImpl
        implements InventoryClientService {

    private final InventoryServiceClient client;

    @Override
    public void reserveStock(
            UUID productId,
            Integer quantity) {

        client.reserveStock(
                ReserveInventoryRequest.builder()
                        .productId(productId)
                        .quantity(quantity)
                        .build());
    }

    @Override
    public void releaseStock(
            UUID productId,
            Integer quantity) {

        client.releaseStock(
                ReleaseInventoryRequest.builder()
                        .productId(productId)
                        .quantity(quantity)
                        .build());
    }

    @Override
    public void confirmStock(
            UUID productId,
            Integer quantity) {

        client.confirmStock(
                ConfirmInventoryRequest.builder()
                        .productId(productId)
                        .quantity(quantity)
                        .build());
    }

}
