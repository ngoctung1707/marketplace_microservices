
package com.marketplace.inventory.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateInventoryRequest {

    @NotNull
    private UUID productId;

    @NotNull
    @Min(0)
    private Integer quantity;

}
