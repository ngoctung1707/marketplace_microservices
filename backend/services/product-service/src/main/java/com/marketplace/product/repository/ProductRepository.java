package com.marketplace.product.repository;

import com.marketplace.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsBySlug(String slug);

    List<Product> findByShopId(UUID shopId);

    List<Product> findByActiveTrue();

    Optional<Product> findByIdAndActiveTrue(UUID id);

}
