package com.example.pilgrimage.Controller;


import com.example.pilgrimage.entity.Admin;
import com.example.pilgrimage.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admins/internal")
@RequiredArgsConstructor
public class InternalUserController {

    @Autowired
    private AdminService adminService;



    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody Admin admin) {
        adminService.saveAdmin(admin);  // ✅ Will now work
        return ResponseEntity.ok("User saved internally");
    }

    @PostMapping("/get")
    public ResponseEntity<Admin> getAdminByEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Admin admin = adminService.getAdminByEmail(email);
        if (admin == null) {
            throw new RuntimeException("Admin not found");
        }
        return ResponseEntity.ok(admin);
    }



}
