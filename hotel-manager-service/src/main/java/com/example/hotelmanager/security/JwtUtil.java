package com.example.hotelmanager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {

    // Shared secret for signing tokens (must match across services)
    private final String secret = "mySuperSecretKeyForJWTs1234567890";

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     *  Generate a JWT with email and role as claims
     */
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 1000)) // 10 hours
                .signWith(getKey())
                .compact();
    }

    /**
     *  Extract email (subject) from token
     */
    public String extractEmail(String token) {
        return getAllClaims(token).getSubject();
    }

    /**
     *  Extract role from token
     */
    public String extractRole(String token) {
        return getAllClaims(token).get("role", String.class);
    }

    /**
     *  Check if token is expired
     */
    public boolean isTokenExpired(String token) {
        return getAllClaims(token).getExpiration().before(new Date());
    }
}
