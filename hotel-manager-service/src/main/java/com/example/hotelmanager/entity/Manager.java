package com.example.hotelmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "managerinfo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false, nullable = false)
    private String hotelId;

    @Column(unique = true, updatable = false, nullable = false)
    private String managerId;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    @Pattern(regexp = ".*\\.(jpg|jpeg|png)$", message = "Profile image must be a valid image file")
    private String imageUrl;

    @NotBlank(message = "Hotel name is required")
    private String hotelName;

    @NotBlank(message = "Hotel address is required")
    private String hotelAddress;

    @NotBlank(message = "Hotel city is required")
    private String hotelCity;

    @NotBlank(message = "Hotel state is required")
    private String hotelState;

    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be 6 digits")
    private String hotelPincode;

    @Size(max = 1000, message = "Hotel description is too long")
    private String hotelDescription;

    @Pattern(regexp = ".*\\.(jpg|jpeg|png)$", message = "Hotel image must be jpg or png")
    private String hotelImageUrl;

    @Min(value = 1, message = "Total rooms must be at least 1")
    private Integer totalRooms;

    @DecimalMin(value = "100.0", message = "Price per night must be at least ₹100")
    private Double pricePerNight;

    @Size(max = 255, message = "Amenities string too long")
    private String amenities;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String createdAt;
}
