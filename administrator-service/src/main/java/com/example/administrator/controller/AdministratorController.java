package com.example.administrator.controller;

import com.example.administrator.dto.AdministratorDto;
import com.example.administrator.entity.Administrator;
import com.example.administrator.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/administrators")
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorService administratorService;

    @PostMapping("/register")
    public ResponseEntity<Administrator> register(@RequestBody AdministratorDto dto) {
        return ResponseEntity.ok(administratorService.registerAdministrator(dto));
    }

    @GetMapping("/profile")
    public ResponseEntity<Administrator> profile(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return ResponseEntity.ok(administratorService.getAdministratorByEmail(email));
    }

    @GetMapping("/internal/administrators/{email}")
    public ResponseEntity<Administrator> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(administratorService.getAdministratorByEmail(email));
    }

}
