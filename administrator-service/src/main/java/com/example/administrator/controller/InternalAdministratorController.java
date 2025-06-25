package com.example.administrator.controller;


import com.example.administrator.entity.Administrator;
import com.example.administrator.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/administrators/internal")
@RequiredArgsConstructor
public class InternalAdministratorController {

    @Autowired
    private AdministratorService administratorService;



    @PostMapping("/save")
    public ResponseEntity<String> saveAdministrator(@RequestBody Administrator administrator) {
        administratorService.saveAdministrator(administrator);  // ✅ Will now work
        return ResponseEntity.ok("Administrator saved internally");
    }

    @PostMapping("/get")
    public ResponseEntity<Administrator> getAdministratorByEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Administrator administrator = administratorService.getAdministratorByEmail(email);
        if (administrator == null) {
            throw new RuntimeException("administrator not found");
        }
        return ResponseEntity.ok(administrator);
    }

}
