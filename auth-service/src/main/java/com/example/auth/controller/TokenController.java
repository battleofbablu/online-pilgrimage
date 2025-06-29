package com.example.auth.controller;

import com.example.auth.dto.LoginResponse;
import com.example.auth.entity.Role;
import com.example.auth.security.CustomUserDetails;
import com.example.auth.security.JwtUtil;
import com.example.auth.service.TokenService;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class TokenController {
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private Role role;

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody String refreshToken) {
        if (!tokenService.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        String email = tokenService.getEmailFromRefreshToken(refreshToken);
        Role role = tokenService.getRoleFromRefreshToken(refreshToken);

        // 🔐 Generate new access token with role
        CustomUserDetails userDetails = new CustomUserDetails(email, "", role);
        String newToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(newToken, refreshToken));
    }
}
