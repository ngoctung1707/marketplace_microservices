package com.marketplace.inventory.service;

import java.util.List;
import java.util.UUID;

import com.marketplace.inventory.dto.internal.InternalInventoryResponse;
import com.marketplace.inventory.dto.request.ConfirmInventoryRequest;
import com.marketplace.inventory.dto.request.CreateInventoryRequest;
import com.marketplace.inventory.dto.request.ReleaseInventoryRequest;
import com.marketplace.inventory.dto.request.ReserveInventoryRequest;
import com.marketplace.inventory.dto.request.UpdateInventoryRequest;
import com.marketplace.inventory.dto.response.InventoryResponse;

public interface InventoryService {

    InventoryResponse createInventory(CreateInventoryRequest request);

    InventoryResponse updateInventory(
            UUID productId,
            UpdateInventoryRequest request);

    InventoryResponse getInventory(UUID productId);

    List<InventoryResponse> getAllInventories();

    void deleteInventory(UUID productId);

    InternalInventoryResponse getInternalInventory(UUID productId);

    void reserveStock(ReserveInventoryRequest request);

    void releaseStock(ReleaseInventoryRequest request);

    void confirmStock(ConfirmInventoryRequest request);

}
