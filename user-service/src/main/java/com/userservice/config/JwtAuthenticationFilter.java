package com.userservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.entity.User;
import com.userservice.service.JwtService;
import com.userservice.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;
    private final UserService userService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    // Inject dependencies using constructor injection (best practice)
    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Get the value of Authorization header from the request
            String authHeader = request.getHeader("Authorization");

            // if AuthHeader is null (doesn't exist) or doesn't start with "Bearer "
            // continue the filter without doing anything (we don't need to authenticate in this endpoint)
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Extract only the token without [Bearer ] and store it
            String accessToken = authHeader.substring("Bearer ".length());

            // Extract all claims from the token (including extraClaims)
            Claims claims = jwtService.extractAllClaims(accessToken);

            // get the subject from the claims which is the user email
            String userEmail = claims.getSubject();

            // If user exists in the claims && the user is not authenticated yet
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load user details by email
                Object userDetails = userService.loadUserByUsername(userEmail);

                // Check that the loaded user is of type User && the token is valid
                if (userDetails instanceof User user && jwtService.isTokenValid(accessToken, user)) {

                    // Log the authentication time
                    logger.info("User : {} is authenticated at {}", userEmail, LocalDateTime.now().format(formatter));

                    // Create an Authentication object with the user details and set it in the SecurityContext
                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } catch (Exception e) {
            // In case of any exception (invalid token, parsing error, etc.)
            logger.error("Authentication failed: {}", e.getMessage());

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            // Return the error message as a JSON response
            String errorResponse = new ObjectMapper().writeValueAsString(
                    Map.of("error", "Invalid or expired JWT token")
            );

            response.getWriter().write(errorResponse);
            return;
        }

        // Continue the filter chain if everything is fine
        filterChain.doFilter(request, response);
    }
}