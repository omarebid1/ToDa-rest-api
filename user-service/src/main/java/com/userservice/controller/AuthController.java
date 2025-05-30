package com.userservice.controller;

import com.userservice.dto.request.LoginRequest;
import com.userservice.dto.request.SignupRequest;
import com.userservice.dto.response.AuthResponse;
import com.userservice.dto.response.LoginResponse;
import com.userservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


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

}
