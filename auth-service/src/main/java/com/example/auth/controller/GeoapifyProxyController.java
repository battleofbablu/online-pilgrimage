package com.example.auth.controller;

import com.example.auth.feign.HotelClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/geoapify/hotels")
@RequiredArgsConstructor
@Slf4j
public class GeoapifyProxyController {

    private final HotelClient hotelClient;

    @GetMapping("/search")
    public ResponseEntity<?> proxyHotelSearch(
            @RequestParam("city") String city,
            @RequestHeader("Authorization") String token
    ) {
        log.info("➡️ Forwarding hotel search for city: {}", city);
        log.info("📦 Forwarding token: {}", token);

        try {
            ResponseEntity<?> response = hotelClient.searchHotelsInternal(city, token);
            log.info("✅ Received response from hotel-service");
            return response;
        } catch (Exception e) {
            log.error("❌ Error calling hotel-service: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Failed to fetch hotels: " + e.getMessage());
        }
    }
}
