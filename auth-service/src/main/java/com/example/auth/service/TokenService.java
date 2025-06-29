package com.example.auth.service;

import com.example.auth.entity.Role;

public interface TokenService {
    String createRefreshToken(String email, Role role);
    boolean validateRefreshToken(String token);
    String getEmailFromRefreshToken(String token);
    Role getRoleFromRefreshToken(String token);

    void cleanupUserTokens(String email, Role role);
    void cleanupAdminTokens(String email, Role role);
    void cleanupAdministratorTokens(String email, Role role);
}