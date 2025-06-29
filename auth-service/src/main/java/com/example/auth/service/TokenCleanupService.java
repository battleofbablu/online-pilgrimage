package com.example.auth.service;

import com.example.auth.entity.Role;
import com.example.auth.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenCleanupService {

    private final TokenRepository tokenRepository;

    @Transactional
    public void deleteOldTokens(String email, Role role) {
        switch (role) {
            case USER -> tokenRepository.deleteUserTokens(email, role);
            case ADMIN -> tokenRepository.deleteAdminTokens(email, role);
            case ADMINISTRATOR -> tokenRepository.deleteAdministratorTokens(email, role);
        }
    }
}
