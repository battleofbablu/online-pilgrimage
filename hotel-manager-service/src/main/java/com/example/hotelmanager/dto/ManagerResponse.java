package com.example.hotelmanager.dto;

import com.example.hotelmanager.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ✅ DTO: ManagerResponse
 * Used to send manager details in API responses (e.g., profile, registration).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerResponse {

    private String managerId;
    private String hotelId;
    private String name;
    private String email;

    private String hotelName;
    private String hotelCity;
    private String hotelState;
    private String hotelAddress;
    private String hotelPincode;

    private String hotelImageUrl;
    private String imageUrl;
    private String hotelDescription;
    private String amenities;
    private String phone;
    private Double pricePerNight;
    private Integer totalRooms;
    private Role role;
}
