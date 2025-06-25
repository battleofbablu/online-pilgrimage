package com.example.auth.service;

import com.example.auth.entity.EmailVerificationCode;
import com.example.auth.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
    private final VerificationCodeRepository repo;

    public String generateCode(String email) {
        String code = String.valueOf(new Random().nextInt(999999));
        repo.save(new EmailVerificationCode(email, code, new Date()));
        return code;
    }

    public boolean verifyCode(String email, String code) {
        return repo.findByEmailAndCode(email, code).isPresent();
    }
}