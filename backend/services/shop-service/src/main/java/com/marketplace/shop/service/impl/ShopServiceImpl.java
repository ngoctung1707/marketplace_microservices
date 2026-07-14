package com.marketplace.shop.service.impl;

import com.marketplace.shop.client.UserServiceClient;
import com.marketplace.shop.dto.request.CreateShopRequest;
import com.marketplace.shop.dto.request.UpdateShopRequest;
import com.marketplace.shop.dto.response.ShopResponse;
import com.marketplace.shop.entity.Shop;
import com.marketplace.shop.exception.OwnerAlreadyHasShopException;
import com.marketplace.shop.exception.OwnerNotFoundException;
import com.marketplace.shop.exception.ShopAlreadyExistsException;
import com.marketplace.shop.exception.ShopNotFoundException;
import com.marketplace.shop.mapper.ShopMapper;
import com.marketplace.shop.repository.ShopRepository;
import com.marketplace.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import feign.FeignException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final UserServiceClient userServiceClient;

    @Override
    public ShopResponse createShop(CreateShopRequest request) {

        if (shopRepository.existsByOwnerId(request.getOwnerId())) {
            throw new OwnerAlreadyHasShopException();
        }

        if (shopRepository.existsByShopName(request.getShopName())) {
            throw new ShopAlreadyExistsException(request.getShopName());
        }
        // Kiểm tra User có tồn tại hay không

        try {
            userServiceClient.getUserByAuthId(request.getOwnerId());
        } catch (FeignException.NotFound ex) {
            throw new OwnerNotFoundException(request.getOwnerId());
        }

        Shop shop = shopMapper.toEntity(request);

        Shop savedShop = shopRepository.save(shop);

        return shopMapper.toResponse(savedShop);
    }

    @Override
    public ShopResponse getShop(UUID id) {

        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ShopNotFoundException(id));

        return shopMapper.toResponse(shop);
    }

    @Override
    public ShopResponse getShopByOwner(UUID ownerId) {

        Shop shop = shopRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new ShopNotFoundException(ownerId));

        return shopMapper.toResponse(shop);
    }

    @Override
    public List<ShopResponse> getAllShops() {

        return shopRepository.findAll()
                .stream()
                .map(shopMapper::toResponse)
                .toList();
    }

    @Override
    public ShopResponse updateShop(UUID id,
            UpdateShopRequest request) {

        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ShopNotFoundException(id));

        if (request.getShopName() != null)
            shop.setShopName(request.getShopName());

        if (request.getDescription() != null)
            shop.setDescription(request.getDescription());

        if (request.getPhone() != null)
            shop.setPhone(request.getPhone());

        if (request.getEmail() != null)
            shop.setEmail(request.getEmail());

        if (request.getAddress() != null)
            shop.setAddress(request.getAddress());

        if (request.getLogoUrl() != null)
            shop.setLogoUrl(request.getLogoUrl());

        if (request.getBannerUrl() != null)
            shop.setBannerUrl(request.getBannerUrl());

        Shop updatedShop = shopRepository.save(shop);

        return shopMapper.toResponse(updatedShop);
    }

    @Override
    public void deleteShop(UUID id) {

        shopRepository.deleteById(id);

    }
}
