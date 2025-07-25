package com.example.hotel.service;

import com.example.hotel.dto.BookingRequest;
import com.example.hotel.dto.BookingResponse;
import com.example.hotel.entity.Booking;
import com.example.hotel.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    /**
     * Create and save a new booking
     * Automatically sets bookedAt timestamp.
     */
    public Booking createBooking(BookingRequest request) {
        Booking booking = Booking.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .hotelName(request.getHotelName())
                .hotelAddress(request.getHotelAddress())
                .roomType(request.getRoomType())
                .guests(request.getGuests())
                .arrivalDate(request.getArrivalDate())
                .arrivalTime(request.getArrivalTime())
                .departureDate(request.getDepartureDate())
                .pickup(request.getPickup())
                .flightNumber(request.getFlightNumber())
                .specialRequests(request.getSpecialRequests())
                .bookedAt(LocalDateTime.now())
                .hotelId(request.getHotelId())
                .managerId(request.getManagerId())
                .build();

        return bookingRepository.save(booking);
    }

    /**
     *  Get bookings by hotelId (Used by internal API or manager dashboard)
     */
    public List<BookingResponse> getBookingsByHotelId(String hotelId) {
        return bookingRepository.findByHotelId(hotelId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     *  Get bookings by managerId (For manager dashboard)
     */
    public List<BookingResponse> getBookingsByManagerId(String managerId) {
        return bookingRepository.findByManagerId(managerId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get bookings made by a specific user (email from JWT principal)
     */
    public List<BookingResponse> getBookingsByEmail(String email) {
        return bookingRepository.findByEmail(email)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     *  Get all bookings (Admin use)
     */
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get bookings by hotel name (used in optional manager filter)
     */
    public List<BookingResponse> getBookingsByHotelName(String hotelName) {
        return bookingRepository.findAll()
                .stream()
                .filter(booking -> booking.getHotelName().equalsIgnoreCase(hotelName))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     *  Entity → DTO mapper for BookingResponse
     */
    private BookingResponse mapToResponse(Booking booking) {
        return BookingResponse.builder()
                .firstName(booking.getFirstName())
                .lastName(booking.getLastName())
                .email(booking.getEmail())
                .hotelName(booking.getHotelName())
                .hotelAddress(booking.getHotelAddress())
                .roomType(booking.getRoomType())
                .guests(booking.getGuests())
                .arrivalDate(booking.getArrivalDate())
                .arrivalTime(booking.getArrivalTime().toString())
                .departureDate(booking.getDepartureDate())
                .pickup(booking.getPickup())
                .flightNumber(booking.getFlightNumber())
                .specialRequests(booking.getSpecialRequests())
                .bookedAt(booking.getBookedAt())
                .build();
    }
}
