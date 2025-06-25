package com.example.pilgrimage.service;

import com.example.pilgrimage.dto.AdminDto;
import com.example.pilgrimage.entity.Admin;

public interface AdminService {
    Admin registerAdmin(AdminDto dto);
    Admin getAdminByEmail(String email);

    void saveAdmin(Admin user);
}
