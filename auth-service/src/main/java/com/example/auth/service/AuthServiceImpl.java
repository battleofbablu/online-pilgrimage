package com.example.auth.service;

import com.example.auth.dto.*;
import com.example.auth.entity.*;
import com.example.auth.feign.*;
import com.example.auth.security.CustomUserDetails;
import com.example.auth.security.JwtUtil;
import com.example.hotelmanager.dto.ManagerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * AuthServiceImpl handles authentication and registration logic
 * for USER, ADMIN, ADMINISTRATOR, and MANAGER roles using Feign clients.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserClient userClient;
    private final AdminClient adminClient;
    private final AdministratorClient administratorClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private final TokenCleanupService tokenCleanupService;

    @Autowired
    private ManagerClient managerClient;

    /**
     * ✅ Register a new user based on their role.
     * Delegates saving to the respective microservice using Feign.
     */
    @Override
    public void register(RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String email = request.getEmail().toLowerCase();
        Role role = request.getRole() != null ? request.getRole() : Role.USER;

        switch (role) {
            case USER -> {
                User user = User.builder()
                        .name(request.getName())
                        .email(email)
                        .password(encodedPassword)
                        .role(role)
                        .build();
                userClient.saveUser(user);
            }
            case ADMIN -> {
                Admin admin = Admin.builder()
                        .name(request.getName())
                        .email(email)
                        .password(encodedPassword)
                        .role(role)
                        .build();
                adminClient.saveadmin(admin);
            }
            case ADMINISTRATOR -> {
                Administrator administrator = Administrator.builder()
                        .name(request.getName())
                        .email(email)
                        .password(encodedPassword)
                        .role(role)
                        .build();
                administratorClient.saveAdministrator(administrator);
            }
            // If needed in future: case MANAGER -> {}
        }
    }

    /**
     * ✅ Login method for USER, ADMIN, ADMINISTRATOR, and MANAGER.
     * Uses role-based logic to validate credentials and generate tokens.
     *
     * @param request LoginRequest with email, password, and loginType
     * @return LoginResponse with JWT and refresh token
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        String loginType = request.getLoginType().toUpperCase();
        String email = request.getEmail();
        Map<String, String> payload = Map.of("email", email);

        switch (loginType) {
            case "ADMIN" -> {
                Admin admin = adminClient.getAdminByEmail(payload);
                validateCredentials(request.getPassword(), admin.getPassword(), "admin");
                return buildLoginResponse(admin.getEmail(), admin.getRole());
            }
            case "ADMINISTRATOR" -> {
                Administrator administrator = administratorClient.getAdministratorByEmail(payload);
                validateCredentials(request.getPassword(), administrator.getPassword(), "administrator");
                return buildLoginResponse(administrator.getEmail(), administrator.getRole());
            }
            case "MANAGER" -> {
                // Login manager via hotel-manager-service
                ManagerLoginRequest managerRequest = new ManagerLoginRequest(
                        request.getEmail(), request.getPassword()
                );
                ManagerResponse manager = managerClient.login(managerRequest);

                if (manager == null || manager.getEmail() == null) {
                    throw new RuntimeException("Invalid manager credentials");
                }

                return buildLoginResponse(manager.getEmail(), Role.MANAGER);
            }
            default -> { // USER
                User user = userClient.getUserByEmail(payload);
                validateCredentials(request.getPassword(), user.getPassword(), "user");
                return buildLoginResponse(user.getEmail(), user.getRole());
            }
        }
    }

    /**
     * ✅ Validate raw password against the encoded password.
     * Throws runtime exception on mismatch.
     */
    private void validateCredentials(String rawPassword, String encodedPassword, String userType) {
        if (encodedPassword == null || !passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new RuntimeException("Invalid " + userType + " credentials");
        }
    }

    /**
     * ✅ Generate JWT and refresh token, and cleanup old tokens.
     */
    private LoginResponse buildLoginResponse(String email, Role role) {
        // Generate JWT token
        String token = jwtUtil.generateToken(email, role.name());

        // Remove previous tokens from DB
        tokenCleanupService.deleteOldTokens(email, role);

        // Create new refresh token
        String refreshToken = tokenService.createRefreshToken(email, role);

        return new LoginResponse(token, refreshToken);
    }
}
