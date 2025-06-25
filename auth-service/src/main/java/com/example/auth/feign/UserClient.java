package com.example.auth.feign;

import com.example.auth.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "user-service", url = "http://localhost:8081/api/users/internal")
public interface UserClient {
    @PostMapping("/save")
    void saveUser(@RequestBody User user);

    @PostMapping("/get")
    User getUserByEmail(@RequestBody Map<String, String> payload);

}
