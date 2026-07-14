package com.marketplace.shop.exception;

import java.util.UUID;

public class ShopNotFoundException extends RuntimeException {

    public ShopNotFoundException(UUID id) {
        super("Shop not found with id: " + id);
    }

}
