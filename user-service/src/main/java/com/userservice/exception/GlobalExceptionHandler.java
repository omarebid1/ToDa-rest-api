package com.userservice.exception;

import com.userservice.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    // Handle UserNotFoundEx
    @ExceptionHandler(WrongOtpEx.class)
    public ResponseEntity<ErrorResponse> handleInvalidOtp(WrongOtpEx ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Wrong OTP",
                ex.getMessage(),
                LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        ErrorResponse error = new ErrorResponse(
                "VALIDATION_ERROR",
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(error);
    }

    // Handle UserNotFoundEx
    @ExceptionHandler(UserNotFoundEx.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundEx(UserNotFoundEx ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "User not found",
                ex.getMessage(),
                LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle EmailAlreadyFoundEx
    @ExceptionHandler(EmailAlreadyFoundEx.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyFoundEx(EmailAlreadyFoundEx ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Email already exists",
                ex.getMessage(),
                LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // Handle InvalidPasswordEx
    @ExceptionHandler(InvalidPasswordEx.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordEx(InvalidPasswordEx ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid password",
                ex.getMessage(),
                LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllOtherExceptions(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Unexpected error occurred",
                ex.getMessage(),
                LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
