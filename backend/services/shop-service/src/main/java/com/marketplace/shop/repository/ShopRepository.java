package com.marketplace.shop.repository;

import com.marketplace.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShopRepository extends JpaRepository<Shop, UUID> {

    boolean existsByOwnerId(UUID ownerId);

    boolean existsByShopName(String shopName);

    Optional<Shop> findByOwnerId(UUID ownerId);
}
