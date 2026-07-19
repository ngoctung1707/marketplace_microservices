package com.marketplace.inventory.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryResponse {

    private UUID id;

    private UUID productId;

    private Integer quantity;

    private Integer reservedQuantity;

    private Integer availableQuantity;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
