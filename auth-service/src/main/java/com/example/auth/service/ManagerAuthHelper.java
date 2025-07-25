package com.example.auth.service;

import com.example.auth.dto.LoginResponse;
import com.example.auth.dto.ManagerLoginRequest;
import com.example.auth.entity.Role;
import com.example.auth.feign.ManagerClient;
import com.example.auth.security.JwtUtil; //  Use your local JwtUtil
import com.example.hotelmanager.dto.ManagerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *  Helper service for authenticating hotel MANAGER users
 * using Feign client to communicate with hotel-manager-service.
 * Generates access + refresh JWT tokens upon successful login.
 */
@Service
@RequiredArgsConstructor
public class ManagerAuthHelper {

    private final ManagerClient managerClient;
    private final JwtUtil jwtUtil; // Use your local JwtUtil from com.example.auth.security
    private final TokenCleanupService tokenCleanupService;
    private final TokenService tokenService;

    /**
     * Perform manager login using Feign call + generate tokens
     *
     * @param request Manager login request (email & password)
     * @return LoginResponse with accessToken and refreshToken
     */
    public LoginResponse login(ManagerLoginRequest request) {
        //  Authenticate using hotel-manager-service
        ManagerResponse manager = managerClient.login(request);

        if (manager == null || manager.getEmail() == null) {
            throw new RuntimeException("Invalid manager credentials");
        }

        String email = manager.getEmail();
        Role role = Role.MANAGER;

        // 🧹 Remove old tokens (cleanup)
        tokenCleanupService.deleteOldTokens(email, role);

        //  Create access token (JWT)
        String accessToken = jwtUtil.generateToken(email, role.name());

        //  Create refresh token
        String refreshToken = tokenService.createRefreshToken(email, role);

        return new LoginResponse(accessToken, refreshToken);
    }
}
