package com.marketplace.auth.exception;

public class UserProfileCreationException extends RuntimeException {

    public UserProfileCreationException() {
        super("Cannot create user profile");
    }

    public UserProfileCreationException(Throwable cause) {
        super("Cannot create user profile", cause);
    }
}