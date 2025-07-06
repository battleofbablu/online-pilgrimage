package com.example.hotel.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {
    private String firstName;
    private String lastName;
    private String email;

    private String hotelName;
    private String hotelAddress;

    private String roomType;
    private int guests;

    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private LocalDate departureDate;

    private String pickup;
    private String flightNumber;
    private String specialRequests;
}
