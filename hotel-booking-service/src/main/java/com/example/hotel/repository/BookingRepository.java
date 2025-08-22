package com.example.hotel.repository;

import com.example.hotel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find bookings by user email
    List<Booking> findByEmail(String email);

    // Existing method to find bookings by hotel name
    List<Booking> findByHotelName(String hotelName);

    // Find by hotelId (Recommended)
    List<Booking> findByHotelId(String hotelId);

    // Find by managerId (For manager dashboard)
    List<Booking> findByManagerId(String managerId);

    @Query("SELECT b FROM Booking b WHERE b.userEmail = :userEmail")
    List<Booking> findByUserEmail(@Param("userEmail") String userEmail);


}
