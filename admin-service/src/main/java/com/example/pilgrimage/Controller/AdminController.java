package com.example.pilgrimage.Controller;

import com.example.pilgrimage.dto.AdminDto;
import com.example.pilgrimage.entity.Admin;
import com.example.pilgrimage.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    @PostMapping("/register")
    public ResponseEntity<Admin> register(@RequestBody AdminDto dto) {
        return ResponseEntity.ok(service.registerAdmin(dto));
    }

    @GetMapping("/profile")
    public ResponseEntity<Admin> profile(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return ResponseEntity.ok(service.getAdminByEmail(email));
    }

    @GetMapping("/internal/admins/{email}")
    public ResponseEntity<Admin> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.getAdminByEmail(email));
    }

}