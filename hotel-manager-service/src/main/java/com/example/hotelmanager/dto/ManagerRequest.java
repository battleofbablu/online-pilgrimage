package com.example.hotelmanager.dto;

import com.example.hotelmanager.entity.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ManagerRequest {
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phone;

    private String imageUrl;

    @NotBlank
    private String hotelName;

    @NotBlank
    private String hotelAddress;

    @NotBlank
    private String hotelCity;

    @NotBlank
    private String hotelState;

    @NotBlank
    private String hotelPincode;

    private String hotelDescription;

    private String hotelImageUrl;

    @Min(1)
    private Integer totalRooms;

    @DecimalMin("0.0")
    private Double pricePerNight;

    private String amenities;

    private Role role;

}
