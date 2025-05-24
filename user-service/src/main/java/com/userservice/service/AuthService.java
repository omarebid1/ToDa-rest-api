package com.userservice.service;

import com.userservice.dto.request.LoginRequest;
import com.userservice.dto.request.SignupRequest;
import com.userservice.dto.response.AuthResponse;
import com.userservice.entity.User;
import com.userservice.enums.Role;
import com.userservice.exception.EmailAlreadyFoundEx;
import com.userservice.exception.InvalidPasswordEx;
import com.userservice.exception.UserNotFoundEx;
import com.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    // LOGIN METHOD
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            // To check user and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Return a basic response without token
            return new AuthResponse(
                    "Login successful",
                    LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter)
            );
        } catch (BadCredentialsException ex) {
            throw new InvalidPasswordEx("Invalid password for user: " + loginRequest.getEmail());
        } catch (UsernameNotFoundException | UserNotFoundEx ex) {
            throw new UserNotFoundEx("User not found with email: " + loginRequest.getEmail());
        }
    }


    // SignUp METHOD
    public AuthResponse register(SignupRequest registerRequest) {
        // Check if the email is already registered
        if (userRepository.findUserByEmail(registerRequest.getEmail()) != null) {
            throw new EmailAlreadyFoundEx("Try with another email, this one is already registered");
        }

        // Hash the password
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());

        // Create and save the new user
        User user = new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                hashedPassword,
                Role.USER // Default role
        );
        userRepository.save(user);

        // Return a simple response
        return new AuthResponse(
                "Registration successful",
                LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter)
        );
    }

}
