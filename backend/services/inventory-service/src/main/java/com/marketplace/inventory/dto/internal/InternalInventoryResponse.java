package com.marketplace.inventory.dto.internal;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InternalInventoryResponse {

    private UUID productId;

    private Integer quantity;

    private Integer reservedQuantity;

    private Integer availableQuantity;

    private Boolean active;

}
