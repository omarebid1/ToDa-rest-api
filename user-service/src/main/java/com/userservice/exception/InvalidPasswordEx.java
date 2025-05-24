package com.userservice.exception;

public class InvalidPasswordEx extends RuntimeException {
    public InvalidPasswordEx(String message) {
        super(message);
    }
}
