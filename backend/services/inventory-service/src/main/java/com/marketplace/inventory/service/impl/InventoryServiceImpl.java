package com.marketplace.inventory.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.inventory.dto.internal.InternalInventoryResponse;
import com.marketplace.inventory.dto.request.ConfirmInventoryRequest;
import com.marketplace.inventory.dto.request.CreateInventoryRequest;
import com.marketplace.inventory.dto.request.ReleaseInventoryRequest;
import com.marketplace.inventory.dto.request.ReserveInventoryRequest;
import com.marketplace.inventory.dto.request.UpdateInventoryRequest;
import com.marketplace.inventory.dto.response.InventoryResponse;
import com.marketplace.inventory.entity.Inventory;
import com.marketplace.inventory.exception.InsufficientStockException;
import com.marketplace.inventory.exception.InventoryAlreadyExistsException;
import com.marketplace.inventory.exception.InventoryInactiveException;
import com.marketplace.inventory.exception.InventoryNotFoundException;
import com.marketplace.inventory.mapper.InventoryMapper;
import com.marketplace.inventory.repository.InventoryRepository;
import com.marketplace.inventory.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    private final InventoryMapper mapper;

    private Inventory findInventory(UUID productId) {

        return repository.findByProductId(productId)
                .orElseThrow(InventoryNotFoundException::new);

    }

    private Inventory buildInventory(CreateInventoryRequest request) {

        if (repository.existsByProductId(request.getProductId())) {
            throw new InventoryAlreadyExistsException();
        }

        return Inventory.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .reservedQuantity(0)
                .active(true)
                .build();

    }

    private InventoryResponse buildResponse(
            Inventory inventory) {

        return mapper.toResponse(inventory);

    }

    private void validateReserve(
            Inventory inventory,
            Integer quantity) {

        if (!Boolean.TRUE.equals(inventory.getActive())) {
            throw new InventoryInactiveException();
        }

        if (inventory.getAvailableQuantity() < quantity) {
            throw new InsufficientStockException();
        }

    }

    private void reserve(
            Inventory inventory,
            Integer quantity) {

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() + quantity);

    }

    private void release(
            Inventory inventory,
            Integer quantity) {

        int reserved = inventory.getReservedQuantity();

        inventory.setReservedQuantity(
                Math.max(0, reserved - quantity));

    }

    private void confirm(
            Inventory inventory,
            Integer quantity) {

        inventory.setQuantity(
                inventory.getQuantity() - quantity);

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() - quantity);

    }

    @Override
    public InventoryResponse createInventory(
            CreateInventoryRequest request) {

        Inventory inventory = buildInventory(request);

        Inventory savedInventory = repository.saveAndFlush(inventory);

        savedInventory = repository.findById(savedInventory.getId())
                .orElseThrow(InventoryNotFoundException::new);

        return buildResponse(savedInventory);

    }

    @Override
    public InventoryResponse updateInventory(
            UUID productId,
            UpdateInventoryRequest request) {

        Inventory inventory = findInventory(productId);

        mapper.updateEntityFromRequest(
                request,
                inventory);

        Inventory savedInventory = repository.saveAndFlush(inventory);

        savedInventory = repository.findById(savedInventory.getId())
                .orElseThrow(InventoryNotFoundException::new);

        return buildResponse(savedInventory);

    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getInventory(UUID productId) {

        Inventory inventory = findInventory(productId);

        return buildResponse(inventory);

    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getAllInventories() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();

    }

    @Override
    public void deleteInventory(UUID productId) {

        Inventory inventory = findInventory(productId);

        inventory.setActive(false);

        repository.save(inventory);

    }

    @Override
    @Transactional(readOnly = true)
    public InternalInventoryResponse getInternalInventory(UUID productId) {

        Inventory inventory = findInventory(productId);

        return mapper.toInternalResponse(inventory);

    }

    @Override
    public void reserveStock(
            ReserveInventoryRequest request) {

        Inventory inventory = findInventory(
                request.getProductId());

        validateReserve(
                inventory,
                request.getQuantity());

        reserve(
                inventory,
                request.getQuantity());

        repository.save(inventory);

    }

    @Override
    public void releaseStock(
            ReleaseInventoryRequest request) {

        Inventory inventory = findInventory(
                request.getProductId());

        release(
                inventory,
                request.getQuantity());

        repository.save(inventory);

    }

    @Override
    public void confirmStock(
            ConfirmInventoryRequest request) {

        Inventory inventory = findInventory(
                request.getProductId());

        confirm(
                inventory,
                request.getQuantity());

        repository.save(inventory);

    }

}
