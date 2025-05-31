package com.userservice.exception;

public class WrongOtpEx extends RuntimeException {
    public WrongOtpEx(String message) {
        super(message);
    }
}
