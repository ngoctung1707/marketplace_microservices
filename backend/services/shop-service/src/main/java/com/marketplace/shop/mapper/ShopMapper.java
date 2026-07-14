package com.marketplace.shop.mapper;

import com.marketplace.shop.dto.request.CreateShopRequest;
import com.marketplace.shop.dto.response.ShopResponse;
import com.marketplace.shop.entity.Shop;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    Shop toEntity(CreateShopRequest request);

    ShopResponse toResponse(Shop shop);

}
