package com.example.hotelmanager.controller;

import com.example.hotelmanager.dto.ManagerLoginDto;
import com.example.hotelmanager.dto.ManagerRequest;
import com.example.hotelmanager.dto.ManagerResponse;
import com.example.hotelmanager.entity.Manager;
import com.example.hotelmanager.security.JwtUtil;
import com.example.hotelmanager.service.ManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * ✅ Login API for managers
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody ManagerLoginDto loginDto) {
        Manager manager = managerService.getManagerByEmail(loginDto.getEmail());

        if (manager == null || !passwordEncoder.matches(loginDto.getPassword(), manager.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        // Generate JWT token with manager's email and role
        String token = jwtUtil.generateToken(manager.getEmail(), manager.getRole().name());
        return ResponseEntity.ok(token);
    }

    /**
     * ✅ Get profile info of the authenticated manager
     */
    @GetMapping("/profile")
    public ResponseEntity<ManagerResponse> getProfile(@RequestHeader("Authorization") String token) {
        // Remove Bearer prefix and extract email from token
        String jwt = token.substring(7);
        String email = jwtUtil.extractEmail(jwt);

        Manager manager = managerService.getManagerByEmail(email);
        if (manager == null) {
            return ResponseEntity.notFound().build();
        }

        // Map manager to response DTO
        ManagerResponse response = ManagerResponse.builder()
                .managerId(manager.getManagerId())
                .name(manager.getName())
                .email(manager.getEmail())
                .hotelName(manager.getHotelName())
                .hotelCity(manager.getHotelCity())
                .hotelState(manager.getHotelState())
                .hotelAddress(manager.getHotelAddress())
                .hotelPincode(manager.getHotelPincode())
                .hotelImageUrl(manager.getHotelImageUrl())
                .imageUrl(manager.getImageUrl())
                .phone(manager.getPhone())
                .pricePerNight(manager.getPricePerNight())
                .totalRooms(manager.getTotalRooms())
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * ✅ Register new manager with profile and hotel images
     */
    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<ManagerResponse> registerManager(
            @RequestPart("manager") String managerJson,
            @RequestPart("hotelImage") MultipartFile hotelImage,
            @RequestPart("profileImage") MultipartFile profileImage) {

        try {
            // Convert JSON to DTO
            ManagerRequest managerRequest = objectMapper.readValue(managerJson, ManagerRequest.class);

            // Create upload directory if not exists
            File uploadDir = new File("uploads/images");
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // Save hotel image
            String hotelFileName = System.currentTimeMillis() + "_hotel_" + hotelImage.getOriginalFilename();
            Path hotelImagePath = Paths.get(uploadDir.getAbsolutePath(), hotelFileName);
            Files.write(hotelImagePath, hotelImage.getBytes());
            managerRequest.setHotelImageUrl("/uploads/images/" + hotelFileName);

            // Save profile image
            String profileFileName = System.currentTimeMillis() + "_profile_" + profileImage.getOriginalFilename();
            Path profileImagePath = Paths.get(uploadDir.getAbsolutePath(), profileFileName);
            Files.write(profileImagePath, profileImage.getBytes());
            managerRequest.setImageUrl("/uploads/images/" + profileFileName);

            // Save to DB
            ManagerResponse response = managerService.registerManager(managerRequest);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/hotels")
    public ResponseEntity<List<ManagerResponse>> getAllHotels() {
        List<Manager> managers = managerService.getAllHotels();

        // Map each manager entity to response DTO
        List<ManagerResponse> hotelList = managers.stream().map(manager -> ManagerResponse.builder()
                .managerId(manager.getManagerId())
                .hotelId(manager.getHotelId())
                .hotelName(manager.getHotelName())
                .hotelCity(manager.getHotelCity())
                .hotelState(manager.getHotelState())
                .hotelAddress(manager.getHotelAddress())
                .hotelPincode(manager.getHotelPincode())
                .hotelImageUrl(manager.getHotelImageUrl())
                .pricePerNight(manager.getPricePerNight())
                .totalRooms(manager.getTotalRooms())
                .hotelDescription(manager.getHotelDescription())
                .amenities(manager.getAmenities())
                .build()
        ).toList();

        return ResponseEntity.ok(hotelList);
    }

}
