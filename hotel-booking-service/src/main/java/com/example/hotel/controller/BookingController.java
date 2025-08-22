package com.example.hotel.controller;

import com.example.hotel.dto.BookingRequest;
import com.example.hotel.dto.BookingResponse;
import com.example.hotel.entity.Booking;
import com.example.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     *  Book a hotel room.
     * Auto-associates booking with the authenticated user's email (via JWT).
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> bookHotel(@RequestBody BookingRequest request, Principal principal) {
        request.setEmail(principal.getName());
        bookingService.createBooking(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Booking successful");
        return ResponseEntity.ok(response);
    }

    /**
     *  Get all bookings for the authenticated USER.
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BookingResponse>> getUserBookings(Principal principal) {
        List<BookingResponse> bookings = bookingService.getBookingsByEmail(principal.getName());
        return ResponseEntity.ok(bookings);
    }

    /**
     *  ADMIN: Get all bookings in the system.
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    /**
     * MANAGER/ADMIN: Get bookings by hotel ID.
     * Useful for dashboard filtering or internal API communication.
     */
    @GetMapping("/hotel-id/{hotelId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponse>> getBookingsByHotelId(@PathVariable String hotelId) {
        return ResponseEntity.ok(bookingService.getBookingsByHotelId(hotelId));
    }

    /**
     * MANAGER/ADMIN: Get bookings by manager ID.
     * Useful for managers to view their users' bookings.
     */
    @GetMapping("/manager/{managerId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponse>> getBookingsByManagerId(@PathVariable String managerId) {
        return ResponseEntity.ok(bookingService.getBookingsByManagerId(managerId));
    }

    /**
     *  Get bookings by hotel name.
     * Can be secured later (currently public).
     */
    @GetMapping("/hotel/{hotelName}")
    public ResponseEntity<List<BookingResponse>> getBookingsByHotel(@PathVariable String hotelName) {
        return ResponseEntity.ok(bookingService.getBookingsByHotelName(hotelName));
    }

    @GetMapping("/book/user/{email}")
    public List<BookingResponse> getBookingsByEmail(@PathVariable String email) {
        return bookingService.getBookingByUserEmail(email);
    }
}
