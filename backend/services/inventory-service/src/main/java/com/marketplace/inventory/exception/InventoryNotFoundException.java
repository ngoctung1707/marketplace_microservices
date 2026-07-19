package com.marketplace.inventory.exception;

public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException() {
        super("Inventory not found.");
    }

}
