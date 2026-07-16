package com.marketplace.product.exception;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(String slug) {
        super("Product already exists with slug: " + slug);
    }

}
