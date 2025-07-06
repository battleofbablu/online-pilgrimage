package com.example.hotel.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Value("${geoapify.api.key}")
    private String geoapifyApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/reverse")
    public ResponseEntity<String> getCityFromCoordinates(
            @RequestParam double lat,
            @RequestParam double lon) {

        String url = String.format(
                "https://api.geoapify.com/v1/geocode/reverse?lat=%f&lon=%f&apiKey=%s",
                lat, lon, geoapifyApiKey
        );

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            JsonNode features = root.path("features");
            if (features.isArray() && features.size() > 0) {
                String city = features.get(0).path("properties").path("city").asText("Unknown");
                return ResponseEntity.ok(city);
            }

            return ResponseEntity.status(404).body("City not found");

        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error parsing Geoapify response");
        }
    }
}
