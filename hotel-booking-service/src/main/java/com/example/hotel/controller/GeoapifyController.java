package com.example.hotel.controller;

import com.example.hotel.dto.CoordinateResponse;
import com.example.hotel.dto.HotelInfo;
import com.example.hotel.service.GeoapifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geoapify/hotels")
@RequiredArgsConstructor
@Slf4j
public class GeoapifyController {

    private final GeoapifyService geoapifyService;

    @GetMapping("/search")
    public ResponseEntity<?> searchHotelsByCity(@RequestParam("city") String city) {
        log.info("🌐 Searching hotels for city: {}", city);

        try {
            // Step 1: Get coordinates from city
            CoordinateResponse coordinates = geoapifyService.getCoordinatesByCity(city);
            log.info("📍 Found coordinates: lat={}, lon={}", coordinates.getLat(), coordinates.getLon());

            // Step 2: Get hotels by coordinates (5km radius)
            List<HotelInfo> hotels = geoapifyService.getHotelsByCoordinates(coordinates.getLat(), coordinates.getLon(), 5000);
            log.info("🏨 Hotels found: {}", hotels.size());

            return ResponseEntity.ok(hotels);

        } catch (Exception e) {
            log.error("❌ Failed to search hotels for city '{}': {}", city, e.getMessage(), e);
            return ResponseEntity.status(500).body(
                    String.format("{\"error\": \"Failed to fetch hotels for city %s: %s\"}", city, e.getMessage())
            );
        }
    }
}
