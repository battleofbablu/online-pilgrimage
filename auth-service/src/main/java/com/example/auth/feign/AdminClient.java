package com.example.auth.feign;

import com.example.auth.entity.Admin;
import com.example.auth.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "admin-service", url = "http://localhost:8082/api/admins/internal")
public interface AdminClient {

    @PostMapping("/save")
    void saveadmin(@RequestBody Admin admin);

    @PostMapping("/get")
    Admin getAdminByEmail(@RequestBody Map<String, String> payload);

}