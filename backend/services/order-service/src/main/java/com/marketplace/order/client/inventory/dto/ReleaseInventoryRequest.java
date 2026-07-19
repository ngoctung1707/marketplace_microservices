
package com.marketplace.order.client.inventory.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReleaseInventoryRequest {

    private UUID productId;

    private Integer quantity;

}
