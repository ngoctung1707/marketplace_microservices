package com.marketplace.order.client.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.marketplace.order.client.inventory.dto.ConfirmInventoryRequest;
import com.marketplace.order.client.inventory.dto.ReleaseInventoryRequest;
import com.marketplace.order.client.inventory.dto.ReserveInventoryRequest;

@FeignClient(name = "inventory-service", path = "/api/v1/internal/inventories")
public interface InventoryServiceClient {

    @PostMapping("/reserve")
    void reserveStock(
            @RequestBody ReserveInventoryRequest request);

    @PostMapping("/release")
    void releaseStock(
            @RequestBody ReleaseInventoryRequest request);

    @PostMapping("/confirm")
    void confirmStock(
            @RequestBody ConfirmInventoryRequest request);

}
