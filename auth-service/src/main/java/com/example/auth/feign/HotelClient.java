package com.example.auth.feign;

import com.example.auth.dto.BookingRequest;
import com.example.auth.dto.BookingResponse;
import com.example.auth.dto.HotelInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "hotel-service", url = "http://localhost:8085")
public interface HotelClient {

    @PostMapping("/api/hotels/internal/book")
    void createBooking(@RequestBody BookingRequest request);

    @PostMapping("/api/hotels/internal/by-user")
    List<BookingResponse> getBookingsByUser(@RequestBody Map<String, String> payload);

    @GetMapping("/api/hotels/internal/all")
    List<BookingResponse> getAllBookings();

    @GetMapping("/api/geoapify/hotels/search")
    ResponseEntity<?> searchHotelsInternal(
            @RequestParam("city") String city,
            @RequestHeader("Authorization") String token
    );


}
