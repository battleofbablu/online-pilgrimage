package com.example.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String hotelName;
    private String hotelAddress;
    private String roomType;
    private int guests;
    private LocalDate arrivalDate;
    private String arrivalTime;
    private LocalDate departureDate;
    private String pickup;
    private String flightNumber;
    private String specialRequests;
    private LocalDateTime bookedAt;
}


