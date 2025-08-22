package com.example.hotel.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ✅ DTO for sending booking data from backend to frontend.
 * Used in response to client or dashboard requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    //  Booking ID (Primary Key)
    private Long id;

    //  Customer Info
    private String firstName;
    private String lastName;
    private String email;

    // Hotel Info
    private String hotelName;
    private String hotelAddress;

    //  Room Preferences
    private String roomType;
    private int guests;

    // Booking Details
    private LocalDate arrivalDate;
    private String arrivalTime; // Converted from LocalTime to String for JSON formatting
    private LocalDate departureDate;

    // ✈ Travel Info
    private String pickup;         // Optional: e.g., Yes/No
    private String flightNumber;   // Optional
    private String specialRequests;

    //  Timestamp of booking
    private LocalDateTime bookedAt;

    private String userEmail;


}
