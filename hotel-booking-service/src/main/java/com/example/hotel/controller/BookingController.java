package com.example.hotel.controller;

import com.example.hotel.dto.BookingRequest;
import com.example.hotel.dto.BookingResponse;
import com.example.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // Create a new hotel booking
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> bookHotel(@RequestBody BookingRequest request, Principal principal) {
        request.setEmail(principal.getName()); // 👤 associate booking with logged-in user
        bookingService.createBooking(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Booking successful");
        return ResponseEntity.ok(response);
    }

    // Get bookings for the logged-in user
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BookingResponse>> getUserBookings(Principal principal) {
        List<BookingResponse> bookings = bookingService.getBookingsByEmail(principal.getName());
        return ResponseEntity.ok(bookings);
    }

    // Optional: Admin endpoint to get all bookings
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
}
