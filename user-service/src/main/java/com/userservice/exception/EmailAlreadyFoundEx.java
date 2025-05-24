package com.userservice.exception;

public class EmailAlreadyFoundEx extends RuntimeException {
    public EmailAlreadyFoundEx(String message) {
        super(message);
    }
}
