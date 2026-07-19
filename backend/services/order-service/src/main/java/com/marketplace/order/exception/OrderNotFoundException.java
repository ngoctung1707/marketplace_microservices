
package com.marketplace.order.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
        super("Order not found.");
    }

}
