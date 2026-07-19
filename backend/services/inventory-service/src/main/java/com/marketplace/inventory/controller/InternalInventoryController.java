package com.marketplace.inventory.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.inventory.dto.internal.InternalInventoryResponse;
import com.marketplace.inventory.dto.request.ConfirmInventoryRequest;
import com.marketplace.inventory.dto.request.ReleaseInventoryRequest;
import com.marketplace.inventory.dto.request.ReserveInventoryRequest;
import com.marketplace.inventory.service.InventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/internal/inventories")
@RequiredArgsConstructor
public class InternalInventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public InternalInventoryResponse getInventory(
            @PathVariable UUID productId) {

        return inventoryService.getInternalInventory(productId);
    }

    @PostMapping("/reserve")
    public void reserveStock(
            @Valid @RequestBody ReserveInventoryRequest request) {

        inventoryService.reserveStock(request);
    }

    @PostMapping("/release")
    public void releaseStock(
            @Valid @RequestBody ReleaseInventoryRequest request) {

        inventoryService.releaseStock(request);
    }

    @PostMapping("/confirm")
    public void confirmStock(
            @Valid @RequestBody ConfirmInventoryRequest request) {

        inventoryService.confirmStock(request);
    }

}
