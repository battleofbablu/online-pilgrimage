package com.example.auth.service;

import com.example.auth.entity.RefreshToken;
import com.example.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public String createRefreshToken(String email) {
        refreshTokenRepository.deleteByEmail(email);
        String token = UUID.randomUUID().toString();
        RefreshToken rt = new RefreshToken(null, email, token,
                new Date(System.currentTimeMillis() + 7 * 86400000));
        refreshTokenRepository.save(rt);
        return token;
    }

    @Override
    public boolean validateRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(rt -> rt.getExpiryDate().after(new Date()))
                .orElse(false);
    }

    @Override
    public String getEmailFromRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(RefreshToken::getEmail).orElse(null);
    }
}
