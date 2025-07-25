package com.example.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Booking {

    @Id
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

