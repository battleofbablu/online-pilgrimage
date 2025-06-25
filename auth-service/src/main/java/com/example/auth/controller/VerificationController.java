package com.example.auth.controller;

import com.example.auth.dto.VerificationCodeRequest;
import com.example.auth.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/verify")
public class VerificationController {
    private final EmailVerificationService service;

    @PostMapping("/send")
    public ResponseEntity<String> sendCode(@RequestBody String email) {
        String code = service.generateCode(email);
        return ResponseEntity.ok("Verification code: " + code);
    }

    @PostMapping("/check")
    public ResponseEntity<String> check(@RequestBody VerificationCodeRequest request) {
        return service.verifyCode(request.getEmail(), request.getCode()) ?
                ResponseEntity.ok("Verified") : ResponseEntity.status(400).body("Invalid code");
    }
}
