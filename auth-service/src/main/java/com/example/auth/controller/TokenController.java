package com.example.auth.controller;

import com.example.auth.dto.LoginResponse;
import com.example.auth.security.CustomUserDetails;
import com.example.auth.security.JwtUtil;
import com.example.auth.service.TokenService;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class TokenController {
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody String refreshToken) {
        if (!tokenService.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(403).build();
        }
        String email = tokenService.getEmailFromRefreshToken(refreshToken);
        String token = jwtUtil.generateToken(new CustomUserDetails(email, "", "USER"));
        return ResponseEntity.ok(new LoginResponse(token, refreshToken));
    }
}
