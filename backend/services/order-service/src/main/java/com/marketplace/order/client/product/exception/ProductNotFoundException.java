package com.marketplace.order.client.product.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Product not found.");
    }

}
