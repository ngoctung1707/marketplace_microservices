package com.marketplace.inventory.exception;

public class InventoryAlreadyExistsException extends RuntimeException {

    public InventoryAlreadyExistsException() {
        super("Inventory already exists.");
    }

}
