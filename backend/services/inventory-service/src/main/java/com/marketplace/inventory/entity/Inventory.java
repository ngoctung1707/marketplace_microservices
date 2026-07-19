package com.marketplace.inventory.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventories", uniqueConstraints = {
        @UniqueConstraint(columnNames = "product_id")
})
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Product Service
     */
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    /**
     * Tổng số lượng trong kho
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Đã giữ để chờ thanh toán
     */
    @Builder.Default
    @Column(name = "reserved_quantity", nullable = false)
    private Integer reservedQuantity = 0;

    /**
     * Soft Delete
     */
    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Transient
    public Integer getAvailableQuantity() {
        return quantity - reservedQuantity;
    }

}
