package com.marketplace.product.exception;

public class ShopNotFoundException extends RuntimeException {

    public ShopNotFoundException() {
        super("Shop not found");
    }

}
