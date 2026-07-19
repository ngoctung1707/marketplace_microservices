package com.marketplace.inventory.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.marketplace.inventory.dto.internal.InternalInventoryResponse;
import com.marketplace.inventory.dto.request.CreateInventoryRequest;
import com.marketplace.inventory.dto.request.UpdateInventoryRequest;
import com.marketplace.inventory.dto.response.InventoryResponse;
import com.marketplace.inventory.entity.Inventory;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    Inventory toEntity(CreateInventoryRequest request);

    @Mapping(target = "availableQuantity", expression = "java(inventory.getAvailableQuantity())")
    InventoryResponse toResponse(Inventory inventory);

    @Mapping(target = "availableQuantity", expression = "java(inventory.getAvailableQuantity())")
    InternalInventoryResponse toInternalResponse(Inventory inventory);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(
            UpdateInventoryRequest request,
            @MappingTarget Inventory inventory);

}
