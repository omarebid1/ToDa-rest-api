package com.userservice.service;

import com.userservice.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret_key;

    private JwtParser jwtParser;

    // Initialize JWT parser using the secret key
    @PostConstruct
    public void init() {
        SecretKey key = Keys.hmacShaKeyFor(secret_key.getBytes());
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    //Generates a JWT token for the given user and claims.
    public String generateToken(User user, Map<String, Object> claims) {
        long accessTokenValidity = 2 * 60 * 1000; // 2 minutes

        // Add custom claim(Role) to the claims
        claims.put("role", user.getRole());

        SecretKey key = Keys.hmacShaKeyFor(secret_key.getBytes(StandardCharsets.UTF_8)); // same as used in init()

        // there are registered claims in JWT like subject, issuer, audience, expiration, issued at etc.
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // extracts all claims from the JWT token
    public Claims extractAllClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    //Extracts the JWT token string from the Authorization header in the HTTP request.
    public String resolveToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // remove "Bearer " prefix
        }
        return null;
    }

    /**
     * Extracts and returns claims from a token found in the HTTP request.
     * Handles token expiration and other exceptions by attaching error messages to the request attributes.
     */
    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return extractAllClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    /**
     * Checks whether the token has expired based on its expiration date.
     * Returns true if the token is expired, false otherwise.
     */
    public boolean isTokenExpired(Date expirationDate) throws AuthenticationException {
        return expirationDate.before(new Date());
    }

    /**
     * Validates the JWT token by checking that:
     * 1. The subject (username) in the token matches the provided UserDetails.
     * 2. The token is not expired.
     */
    public boolean isTokenValid(String accessToken, User user) {
        String username = user.getUsername();
        Claims claims = extractAllClaims(accessToken);
        return username.equals(claims.getSubject()) && !isTokenExpired(claims.getExpiration());
    }


    // Assumes roles are stored under the "roles" key in the token.
    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }
}

