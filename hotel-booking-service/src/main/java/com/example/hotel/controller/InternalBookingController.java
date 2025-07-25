// In Hotel Booking Service (8085) project
package com.example.hotel.controller;

import com.example.hotel.dto.BookingRequest;
import com.example.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings/internal")
@RequiredArgsConstructor
public class InternalBookingController {

    private final BookingService bookingService;

    @PostMapping("/save")
    public void saveBooking(@RequestBody BookingRequest request) {
        bookingService.createBooking(request);
    }
}

