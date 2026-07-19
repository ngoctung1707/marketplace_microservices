package com.marketplace.order.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marketplace.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> findByIdAndActiveTrue(UUID id);

    Optional<Order> findByOrderCode(String orderCode);

    List<Order> findByUserIdAndActiveTrue(UUID userId);

    boolean existsByOrderCode(String orderCode);

}
