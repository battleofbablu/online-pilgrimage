package com.example.demo.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.Model.Booking;

public interface BookingService 
{
	public ResponseEntity<Booking> registerBooking(Booking b1);

	    // Get all bookings 
	public List<Booking> getAllBookings();

	    // Get bookings for a specific temple for temple-wise reports)
	public List<Booking> getBookingsByTempleId(String templeId);

	    // Get booking by ID
	 public Booking getBookingById(String bookingId);
}
