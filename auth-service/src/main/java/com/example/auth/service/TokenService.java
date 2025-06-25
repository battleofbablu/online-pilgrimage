package com.example.auth.service;

public interface TokenService {
    String createRefreshToken(String email);
    boolean validateRefreshToken(String token);
    String getEmailFromRefreshToken(String token);
}