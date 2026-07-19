package com.marketplace.order.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotNull
    private UUID userId;

    @NotBlank
    private String receiverName;

    @NotBlank
    private String receiverPhone;

    @NotBlank
    private String shippingAddress;

    @Valid
    @NotEmpty
    private List<CreateOrderItemRequest> items;

}
