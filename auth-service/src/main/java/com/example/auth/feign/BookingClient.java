package com.example.auth.feign;

import com.example.auth.dto.BookingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feign client for communicating with the hotel-booking-service.
 */
@FeignClient(name = "hotel-booking-service", url = "http://localhost:8085")
public interface BookingClient {

    /**
     *  Forward booking request to Booking Service (internal endpoint).
     * @param request BookingRequest DTO
     */
    @PostMapping("/api/bookings/internal/save")
    void saveBooking(@RequestBody BookingRequest request);

    /**
     *  Get list of user emails who booked through a specific manager.
     * @param managerId ID of the manager
     * @return List of user emails
     */
    @GetMapping("/api/bookings/internal/manager/{managerId}/emails")
    List<String> getUserEmailsByManagerId(@PathVariable("managerId") String managerId);
}
