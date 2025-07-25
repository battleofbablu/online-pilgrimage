package com.example.hotel.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * ✅ DTO for user to request a hotel booking.
 * This class carries booking information from frontend to backend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    // User Info
    private String firstName;
    private String lastName;
    private String email; // Will be set automatically from JWT in controller

    //  Hotel Info
    private String hotelName;
    private String hotelAddress;

    //  Room Preferences
    private String roomType;
    private int guests;

    // Booking Dates
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private LocalDate departureDate;

    //  Travel Details
    private String pickup;         // e.g., "Yes"/"No" or pickup location
    private String flightNumber;   // Optional
    private String specialRequests; // Optional special notes

    //  Links to manager/hotel (for filtering and dashboards)
    private String hotelId;
    private String managerId;
}
