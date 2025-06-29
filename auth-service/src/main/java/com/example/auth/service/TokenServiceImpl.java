package com.example.auth.service;

import com.example.auth.entity.RefreshToken;
import com.example.auth.entity.Role;
import com.example.auth.repository.RefreshTokenRepository;
import com.example.auth.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenRepository tokenRepository;

    // ✅ Create and store a new refresh token after deleting old ones
    @Override
    @Transactional
    public String createRefreshToken(String email, Role role) {
        System.out.println("Cleaning refresh tokens for: " + email);
        refreshTokenRepository.deleteByEmail(email); // must be inside a transaction
        System.out.println("Deleted old refresh tokens");

        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken(
                null,
                email,
                token,
                role,
                new Date(System.currentTimeMillis() + 7 * 86400000L) // 7 days
        );
        refreshTokenRepository.save(refreshToken);
        System.out.println("Saved new refresh token: " + token);

        return token;
    }

    // ✅ Validate if refresh token is not expired
    @Override
    public boolean validateRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(rt -> rt.getExpiryDate().after(new Date()))
                .orElse(false);
    }

    // ✅ Get email associated with the refresh token
    @Override
    public String getEmailFromRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(RefreshToken::getEmail)
                .orElse(null);
    }

    // ✅ Get role associated with the refresh token
    @Override
    public Role getRoleFromRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(RefreshToken::getRole)
                .orElse(null);
    }

    // ✅ Cleanup for User tokens
    @Override
    @Transactional
    public void cleanupUserTokens(String email, Role role) {
        tokenRepository.deleteUserTokens(email, role);
    }

    // ✅ Cleanup for Admin tokens
    @Override
    @Transactional
    public void cleanupAdminTokens(String email, Role role) {
        tokenRepository.deleteAdminTokens(email, role);
    }

    // ✅ Cleanup for Administrator tokens
    @Override
    @Transactional
    public void cleanupAdministratorTokens(String email, Role role) {
        tokenRepository.deleteAdministratorTokens(email, role);
    }
}
