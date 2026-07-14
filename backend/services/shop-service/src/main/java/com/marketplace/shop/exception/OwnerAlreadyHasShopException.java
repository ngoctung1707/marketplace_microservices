package com.marketplace.shop.exception;

public class OwnerAlreadyHasShopException extends RuntimeException {

    public OwnerAlreadyHasShopException() {
        super("Owner already has a shop");
    }

}
