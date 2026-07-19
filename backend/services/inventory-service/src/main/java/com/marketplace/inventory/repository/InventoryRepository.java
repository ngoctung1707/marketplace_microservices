package com.marketplace.inventory.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marketplace.inventory.entity.Inventory;

public interface InventoryRepository
        extends JpaRepository<Inventory, UUID> {

    Optional<Inventory> findByProductId(UUID productId);

    boolean existsByProductId(UUID productId);

}
