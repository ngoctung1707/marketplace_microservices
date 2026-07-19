package com.marketplace.inventory.exception;

public class InventoryInactiveException extends RuntimeException {

    public InventoryInactiveException() {
        super("Inventory is inactive.");
    }

}
