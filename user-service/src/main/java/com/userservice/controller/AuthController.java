package com.userservice.controller;

import com.userservice.dto.request.LoginRequest;
import com.userservice.dto.request.PasswordResetRequest;
import com.userservice.dto.request.SignupRequest;
import com.userservice.dto.response.AuthResponse;
import com.userservice.dto.response.LoginResponse;
import com.userservice.service.AuthService;
import com.userservice.service.OtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/rest/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and password reset")
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;

    @Operation(
            summary = "Login user",
            description = "Authenticate user with email and password and return a JWT token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody(description = "User login credentials", required = true)
                                               @org.springframework.web.bind.annotation.RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Operation(
            summary = "Register new user",
            description = "Register a new user account with the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody(description = "User registration data", required = true)
            @org.springframework.web.bind.annotation.RequestBody SignupRequest signupRequest
    ) {
        return ResponseEntity.ok(authService.register(signupRequest));
    }

    @Operation(
            summary = "Request OTP",
            description = "Request an OTP code to be sent to the user's email for password reset."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP sent successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "404", description = "Email not found")
    })
    @PostMapping("/request-otp")
    public ResponseEntity<AuthResponse> requestOtp(
            @Parameter(description = "User email", required = true)
            @RequestParam String email
    ) {
        return ResponseEntity.ok(otpService.generateAndSendOtp(email));
    }

    @Operation(
            summary = "Reset password using OTP",
            description = "Reset user password after verifying the OTP sent to their email."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successful",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid OTP or request data")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<AuthResponse> resetPassword(
            @Valid @RequestBody(description = "Password reset request with OTP", required = true)
            @org.springframework.web.bind.annotation.RequestBody PasswordResetRequest resetReq
    ) {
        return ResponseEntity.ok(otpService.verifyOtpAndResetPassword(
                resetReq.getEmail(),
                resetReq.getOtp(),
                resetReq.getNewPassword()
        ));
    }
}