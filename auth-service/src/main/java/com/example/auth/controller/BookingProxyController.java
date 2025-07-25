package com.example.auth.controller;

import com.example.auth.dto.BookingRequest;
import com.example.auth.feign.BookingClient;
import com.example.auth.feign.ManagerClient;
import com.example.hotelmanager.dto.ManagerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingProxyController {

    private final BookingClient bookingClient;
    private final ManagerClient managerClient;

    /**
     * ✅ Forward booking request from Auth Service to Booking Service
     */
    @PostMapping("/booking")
    public ResponseEntity<String> forwardBooking(@RequestBody BookingRequest request) {
        bookingClient.saveBooking(request);
        return ResponseEntity.ok("Booking forwarded successfully from Auth Service");
    }

    /**
     * ✅ Manager Dashboard: Get list of booked user emails using manager JWT
     */
    @GetMapping("/manager/bookings")
    public ResponseEntity<List<String>> getBookedUsersByManager(@RequestHeader("Authorization") String token) {
        // 1️⃣ Fetch manager profile using token
        ManagerResponse manager = managerClient.getManagerProfile(token);

        // 2️⃣ Use manager ID to get user emails from Booking Service
        List<String> emails = bookingClient.getUserEmailsByManagerId(manager.getManagerId());

        return ResponseEntity.ok(emails);
    }
}
