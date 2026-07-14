package com.marketplace.shop.exception;

public class ShopAlreadyExistsException extends RuntimeException {

    public ShopAlreadyExistsException(String shopName) {
        super("Shop name already exists: " + shopName);
    }

}
