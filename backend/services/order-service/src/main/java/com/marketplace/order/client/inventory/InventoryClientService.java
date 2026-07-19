package com.marketplace.order.client.inventory;

import java.util.UUID;

public interface InventoryClientService {

    void reserveStock(
            UUID productId,
            Integer quantity);

    void releaseStock(
            UUID productId,
            Integer quantity);

    void confirmStock(
            UUID productId,
            Integer quantity);

}
