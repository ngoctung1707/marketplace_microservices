package com.marketplace.shop.service;

import com.marketplace.shop.dto.request.CreateShopRequest;
import com.marketplace.shop.dto.request.UpdateShopRequest;
import com.marketplace.shop.dto.response.ShopResponse;

import java.util.List;
import java.util.UUID;

public interface ShopService {

    ShopResponse createShop(CreateShopRequest request);

    ShopResponse getShop(UUID id);

    ShopResponse getShopByOwner(UUID ownerId);

    List<ShopResponse> getAllShops();

    ShopResponse updateShop(UUID id, UpdateShopRequest request);

    void deleteShop(UUID id);

}
