package com.example.hotel.entity;

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

    private String hotelId;  // Reference to hotel
    private String managerId; // Reference to manager (optional but recommended for filtering)

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", hotelAddress='" + hotelAddress + '\'' +
                ", roomType='" + roomType + '\'' +
                ", guests=" + guests +
                ", arrivalDate=" + arrivalDate +
                ", arrivalTime=" + arrivalTime +
                ", departureDate=" + departureDate +
                ", pickup='" + pickup + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", specialRequests='" + specialRequests + '\'' +
                ", bookedAt=" + bookedAt +
                ", hotelId='" + hotelId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }

    @Column(name = "user_email")
    private String userEmail;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }



}
