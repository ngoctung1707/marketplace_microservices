package com.marketplace.inventory.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.inventory.dto.request.CreateInventoryRequest;
import com.marketplace.inventory.dto.request.UpdateInventoryRequest;
import com.marketplace.inventory.dto.response.InventoryResponse;
import com.marketplace.inventory.service.InventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse createInventory(
            @Valid @RequestBody CreateInventoryRequest request) {

        return inventoryService.createInventory(request);
    }

    @GetMapping("/{productId}")
    public InventoryResponse getInventory(
            @PathVariable("productId") UUID productId) {

        return inventoryService.getInventory(productId);
    }

    @GetMapping
    public List<InventoryResponse> getAllInventories() {

        return inventoryService.getAllInventories();
    }

    @PutMapping("/{productId}")
    public InventoryResponse updateInventory(
            @PathVariable("productId") UUID productId,
            @Valid @RequestBody UpdateInventoryRequest request) {

        return inventoryService.updateInventory(productId, request);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(
            @PathVariable("productId") UUID productId) {

        inventoryService.deleteInventory(productId);
    }

}
