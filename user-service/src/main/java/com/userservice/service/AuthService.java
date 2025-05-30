package com.userservice.service;

import com.userservice.dto.request.LoginRequest;
import com.userservice.dto.request.SignupRequest;
import com.userservice.dto.response.AuthResponse;
import com.userservice.dto.response.LoginResponse;
import com.userservice.entity.Jwt;
import com.userservice.entity.User;
import com.userservice.enums.Role;
import com.userservice.exception.EmailAlreadyFoundEx;
import com.userservice.exception.InvalidPasswordEx;
import com.userservice.exception.UserNotFoundEx;
import com.userservice.repository.JwtRepository;
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
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    JwtRepository jwtRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    // LOGIN METHOD
    public LoginResponse login(LoginRequest req) {
        try {
            // To check user and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getEmail(),
                            req.getPassword()
                    )
            );

            User user = userRepository.findUserByEmail(req.getEmail());
            Map<String, Object> extraClaims = new HashMap<>();
            String jwtToken = jwtService.generateToken(user, extraClaims);
            saveUserToken(user, jwtToken);

            // Return a basic response without token
            return new LoginResponse(
                    "Login successful",
                    LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter),
                    jwtToken
            );
        } catch (BadCredentialsException ex) {
            throw new InvalidPasswordEx("Invalid password for user: " + req.getEmail());
        } catch (UsernameNotFoundException | UserNotFoundEx ex) {
            throw new UserNotFoundEx("User not found with email: " + req.getEmail());
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

    private void saveUserToken(User user, String jwtToken) {
        // Create a new Jwt token and save it to the repository
        Jwt jwt = new Jwt();
        jwt.setUser(user);
        jwt.setToken(jwtToken);
        jwt.setEnabled(true);
        jwt.setCreatedAt(LocalDateTime.now());
        jwt.setExpirationDate(LocalDateTime.now().plusMinutes(15)); // Set expiration to 15 minutes from now

        jwtRepository.save(jwt);

    }

}
