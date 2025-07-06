package com.example.auth.service;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.LoginResponse;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.entity.*;
import com.example.auth.feign.AdminClient;
import com.example.auth.feign.AdministratorClient;
import com.example.auth.feign.UserClient;
import com.example.auth.security.CustomUserDetails;
import com.example.auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    @Override
    public void register(RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String email = request.getEmail().toLowerCase();
        Role assignedRole = request.getRole() != null ? request.getRole() : Role.USER;

        switch (assignedRole) {
            case USER -> {
                User user = User.builder()
                        .name(request.getName())
                        .email(email)
                        .password(encodedPassword)
                        .role(assignedRole)
                        .build();
                userClient.saveUser(user);
                System.out.println("User registered: " + email);
            }
            case ADMIN -> {
                Admin admin = Admin.builder()
                        .name(request.getName())
                        .email(email)
                        .password(encodedPassword)
                        .role(assignedRole)
                        .build();
                adminClient.saveadmin(admin);
                System.out.println("Admin registered: " + email);
            }
            case ADMINISTRATOR -> {
                Administrator administrator = Administrator.builder()
                        .name(request.getName())
                        .email(email)
                        .password(encodedPassword)
                        .role(assignedRole)
                        .build();
                administratorClient.saveAdministrator(administrator);
                System.out.println("Administrator registered: " + email);
            }
        }
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        CustomUserDetails userDetails;
        String loginType = request.getLoginType().toUpperCase();
        Map<String, String> payload = Map.of("email", request.getEmail());

        switch (loginType) {
            case "ADMIN":
                Admin admin = adminClient.getAdminByEmail(payload);
                System.out.println("Fetched Admin: " + admin);
                if (admin == null || !passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
                    throw new RuntimeException("Invalid admin credentials");
                }
                userDetails = new CustomUserDetails(admin);
                break;

            case "ADMINISTRATOR":
                Administrator administrator = administratorClient.getAdministratorByEmail(payload);
                if (administrator == null || !passwordEncoder.matches(request.getPassword(), administrator.getPassword())) {
                    throw new RuntimeException("Invalid administrator credentials");
                }
                userDetails = new CustomUserDetails(administrator);
                break;

            default: // USER
                User user = userClient.getUserByEmail(payload);
                if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                    throw new RuntimeException("Invalid user credentials");
                }
                userDetails = new CustomUserDetails(user);
                break;
        }

        String token = jwtUtil.generateToken(userDetails);

        // 🧹 Cleanup old tokens before saving a new one
        tokenCleanupService.deleteOldTokens(request.getEmail(), userDetails.getRole());

        String refreshToken = tokenService.createRefreshToken(
                request.getEmail(),
                userDetails.getRole()
        );

        return new LoginResponse(token, refreshToken);
    }

}
