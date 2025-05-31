package com.userservice.controller;

import com.userservice.dto.request.LoginRequest;
import com.userservice.dto.request.PasswordResetRequest;
import com.userservice.dto.request.SignupRequest;
import com.userservice.dto.response.AuthResponse;
import com.userservice.dto.response.LoginResponse;
import com.userservice.service.AuthService;
import com.userservice.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;


    // LOGIN ENDPOINT
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    // REGISTER ENDPOINT
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(authService.register(signupRequest));
    }

    @PostMapping("/request-otp")
    public ResponseEntity<AuthResponse> requestOtp(@RequestParam String email) {
        return ResponseEntity.ok(otpService.generateAndSendOtp(email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<AuthResponse> resetPassword(@RequestBody PasswordResetRequest ResetReq) {
        return ResponseEntity.ok(otpService.verifyOtpAndResetPassword(
                ResetReq.getEmail(),
                ResetReq.getOtp(),
                ResetReq.getNewPassword()
        ));
    }
}
