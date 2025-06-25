package com.example.auth.feign;

import com.example.auth.entity.Admin;
import com.example.auth.entity.Administrator;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "administrator-service", url = "http://localhost:8083/api/administrators/internal")
public interface AdministratorClient {

    @PostMapping("/save")
    void saveAdministrator(@RequestBody Administrator administrator);

    @PostMapping("/get")
    Administrator getAdministratorByEmail(@RequestBody Map<String, String> payload);

}