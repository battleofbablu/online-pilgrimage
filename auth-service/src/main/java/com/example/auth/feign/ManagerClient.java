package com.example.auth.feign;

import com.example.auth.dto.ManagerLoginRequest;
import com.example.hotelmanager.dto.ManagerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Feign client to interact with the Hotel Manager Service.
 */
@FeignClient(name = "hotel-manager-service", url = "http://localhost:8087")
public interface ManagerClient {

    /**
     * Login manager using credentials (used internally for service-to-service auth).
     * @param request ManagerLoginRequest with email and password
     * @return ManagerResponse containing basic manager info
     */
    @PostMapping("/api/managers/login")
    ManagerResponse login(@RequestBody ManagerLoginRequest request);

    /**
     *  Get manager profile by JWT token (used to identify manager via Auth Service).
     * @param token JWT Bearer token
     * @return ManagerResponse (should be typed instead of Object ideally)
     */
    @GetMapping("/api/managers/profile")
    ManagerResponse getManagerProfile(@RequestHeader("Authorization") String token);
}
