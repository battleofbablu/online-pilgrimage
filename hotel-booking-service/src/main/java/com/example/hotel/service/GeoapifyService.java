package com.example.hotel.service;

import com.example.hotel.dto.CoordinateResponse;
import com.example.hotel.dto.HotelInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeoapifyService {

    @Value("${geoapify.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // 1. Get coordinates from city name
    public CoordinateResponse getCoordinatesByCity(String city) {
        try {
            String url = "https://api.geoapify.com/v1/geocode/search?text=" +
                    URLEncoder.encode(city, "UTF-8") +
                    "&apiKey=" + apiKey;

            log.info("Calling Geoapify for city coordinates: {}", url);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = new ObjectMapper().readTree(response.getBody());
                JsonNode features = root.path("features");

                if (features.isArray() && features.size() > 0) {
                    JsonNode geometry = features.get(0).path("geometry");
                    double lon = geometry.path("coordinates").get(0).asDouble();
                    double lat = geometry.path("coordinates").get(1).asDouble();
                    return new CoordinateResponse(lat, lon);
                } else {
                    throw new RuntimeException("No coordinates found for city: " + city);
                }
            } else {
                throw new RuntimeException("Failed to fetch coordinates: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Error fetching coordinates for city {}: {}", city, e.getMessage());
            throw new RuntimeException("Error fetching coordinates: " + e.getMessage(), e);
        }
    }

    // 2. Get hotels by coordinates — only hotels for stay (no food places)
    public List<HotelInfo> getHotelsByCoordinates(double lat, double lon, int radius) {
        String url = String.format(
                "https://api.geoapify.com/v2/places?categories=accommodation.hotel&filter=circle:%.6f,%.6f,%d&limit=20&apiKey=%s",
                lon, lat, radius, apiKey
        );

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            log.info("Fetching hotels from Geoapify: {}", url);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            List<HotelInfo> hotelList = new ArrayList<>();

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = new ObjectMapper().readTree(response.getBody());
                JsonNode features = root.path("features");

                for (JsonNode feature : features) {
                    JsonNode properties = feature.path("properties");
                    JsonNode geometry = feature.path("geometry");

                    HotelInfo hotel = new HotelInfo();
                    hotel.setName(properties.path("name").asText(""));
                    hotel.setAddressLine1(properties.path("address_line1").asText(""));
                    hotel.setAddressLine2(properties.path("address_line2").asText(""));
                    hotel.setLongitude(geometry.path("coordinates").get(0).asDouble());
                    hotel.setLatitude(geometry.path("coordinates").get(1).asDouble());

                    // 🏷️ Add mock price & rating
                    hotel.setPrice(1000 + (Math.random() * 4000)); // ₹1000 - ₹5000
                    hotel.setRating(2.5 + (Math.random() * 2.5));  // 2.5 - 5.0 stars

                    hotelList.add(hotel);
                }
            }

            return hotelList;

        } catch (Exception e) {
            log.error("Error parsing hotels: {}", e.getMessage(), e);
            throw new RuntimeException("Error parsing hotel response from Geoapify");
        }
    }
}
