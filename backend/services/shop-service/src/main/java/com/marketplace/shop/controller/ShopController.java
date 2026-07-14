package com.marketplace.shop.controller;

import com.marketplace.shop.dto.request.CreateShopRequest;
import com.marketplace.shop.dto.request.UpdateShopRequest;
import com.marketplace.shop.dto.response.ShopResponse;
import com.marketplace.shop.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    public ShopResponse createShop(
            @Valid @RequestBody CreateShopRequest request) {

        return shopService.createShop(request);
    }

    @GetMapping("/{id}")
    public ShopResponse getShop(
            @PathVariable("id") UUID id) {

        return shopService.getShop(id);
    }

    @GetMapping("/owner/{ownerId}")
    public ShopResponse getShopByOwner(
            @PathVariable("ownerId") UUID ownerId) {

        return shopService.getShopByOwner(ownerId);
    }

    @GetMapping
    public List<ShopResponse> getAllShops() {

        return shopService.getAllShops();
    }

    @PutMapping("/{id}")
    public ShopResponse updateShop(
            @PathVariable("id") UUID id,
            @RequestBody UpdateShopRequest request) {

        return shopService.updateShop(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteShop(
            @PathVariable("id") UUID id) {

        shopService.deleteShop(id);
    }

}
