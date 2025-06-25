package com.example.pilgrimage.service.adminServiceImpl;

import com.example.pilgrimage.dto.AdminDto;
import com.example.pilgrimage.entity.Admin;
import com.example.pilgrimage.repository.AdminRepository;
import com.example.pilgrimage.service.AdminService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    @Autowired
    private final AdminRepository adminRepository;

    @Override
    public Admin registerAdmin(AdminDto dto) {
        Admin admin = Admin.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
        return adminRepository.save(admin);
    }

    @Override
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Admin not found"));
    }
    @Override
    public void saveAdmin(Admin user) {
        adminRepository.save(user);
    }
}
