package com.example.hotel.controller;

import com.example.hotel.dto.BookingRequest;
import com.example.hotel.dto.BookingResponse;
import com.example.hotel.dto.CoordinateResponse;
import com.example.hotel.dto.HotelInfo;
import com.example.hotel.service.BookingService;
import com.example.hotel.service.GeoapifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200") // Add CORS support for frontend
@RestController
@RequestMapping("/api/hotels/internal/geoapify")
@RequiredArgsConstructor
public class InternalHotelController {

    private final GeoapifyService geoapifyService;
    private final BookingService bookingService;

    // ✅ Book a hotel (from Angular form)
    @PostMapping("/book")
    public ResponseEntity<String> createBooking(@RequestBody BookingRequest request) {
        bookingService.createBooking(request);  // ✅ fixed method call
        return ResponseEntity.ok("Booking saved successfully.");
    }

    // ✅ Get bookings for a specific user by email
    @PostMapping("/by-user")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        return ResponseEntity.ok(bookingService.getBookingsByEmail(email));
    }

    // ✅ Get all bookings (for admin)
    @GetMapping("/all")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // ✅ Hotel search by city → fetches coordinates and nearby hotels
    @GetMapping("/internal/search")
    public ResponseEntity<List<HotelInfo>> searchHotelsInternal(@RequestParam String city) {
        CoordinateResponse coordinates = geoapifyService.getCoordinatesByCity(city);
        List<HotelInfo> hotels = geoapifyService.getHotelsByCoordinates(
                coordinates.getLat(), coordinates.getLon(), 5000
        );
        return ResponseEntity.ok(hotels);
    }
}
