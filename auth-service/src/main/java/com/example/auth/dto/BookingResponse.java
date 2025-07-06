package com.example.auth.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponse {
    private String hotelName;
    private String location;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String userEmail;
}

