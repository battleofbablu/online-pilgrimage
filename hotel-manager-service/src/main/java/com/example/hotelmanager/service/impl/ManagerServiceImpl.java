package com.example.hotelmanager.service.impl;

import com.example.hotelmanager.dto.ManagerRequest;
import com.example.hotelmanager.dto.ManagerResponse;
import com.example.hotelmanager.entity.Manager;
import com.example.hotelmanager.entity.Role;
import com.example.hotelmanager.repository.ManagerRepository;
import com.example.hotelmanager.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new manager with encoded password and auto-generated IDs.
     */
    @Override
    public ManagerResponse registerManager(ManagerRequest request) {
        // Prevent duplicate registrations
        if (managerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        // 🔄 Auto-generate unique IDs using timestamp
        String managerId = "MGR" + System.currentTimeMillis();
        String hotelId = "HOTEL" + System.currentTimeMillis();

        // 🧠 Handle null role: fallback to MANAGER
        Role assignedRole = request.getRole() != null ? request.getRole() : Role.MANAGER;

        // Map request to entity
        Manager manager = Manager.builder()
                .managerId(managerId)
                .hotelId(hotelId)
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .imageUrl(request.getImageUrl())
                .hotelName(request.getHotelName())
                .hotelAddress(request.getHotelAddress())
                .hotelCity(request.getHotelCity())
                .hotelState(request.getHotelState())
                .hotelPincode(request.getHotelPincode())
                .hotelDescription(request.getHotelDescription())
                .hotelImageUrl(request.getHotelImageUrl())
                .totalRooms(request.getTotalRooms())
                .pricePerNight(request.getPricePerNight())
                .amenities(request.getAmenities())
                .role(assignedRole)  // ✅ Safe assignment
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        // Save to DB
        managerRepository.save(manager);

        // Prepare response DTO
        ManagerResponse response = new ManagerResponse();
        response.setManagerId(manager.getManagerId());
        response.setName(manager.getName());
        response.setEmail(manager.getEmail());
        response.setHotelName(manager.getHotelName());
        response.setHotelCity(manager.getHotelCity());
        response.setHotelState(manager.getHotelState());
        response.setRole(manager.getRole());

        return response;
    }

    /**
     * Fetch manager by email (used in login/profile)
     */
    @Override
    public Manager getManagerByEmail(String email) {
        return managerRepository.findByEmail(email).orElse(null);
    }



    @Override
    public List<Manager> getAllHotels() {
        return managerRepository.findAll();
    }
}
