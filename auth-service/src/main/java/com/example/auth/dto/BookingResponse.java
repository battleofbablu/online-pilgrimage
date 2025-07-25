package com.example.auth.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class BookingResponse {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(length = 1000)
    private String specialRequests;

    private LocalDateTime bookedAt;


}

