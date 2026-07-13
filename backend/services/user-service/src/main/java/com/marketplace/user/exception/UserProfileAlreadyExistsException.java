package com.marketplace.user.exception;

public class UserProfileAlreadyExistsException extends RuntimeException {

    public UserProfileAlreadyExistsException() {
        super("User profile already exists");
    }
}