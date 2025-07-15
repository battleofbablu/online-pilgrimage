package com.example.demo.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Booking;
import com.example.demo.Repo.BookingRepo;
import com.example.demo.Service.BookingService;

@Service
public class BookingDao implements BookingService {
     @Autowired
	 BookingRepo br;
     
	@Override
	public ResponseEntity<Booking> registerBooking(Booking b1) {
		Booking savedBooking = br.save(b1);
		return ResponseEntity.ok(savedBooking);
	}

	@Override
	public List<Booking> getAllBookings() {
	      return br.findAll();
	}

	@Override
	public List<Booking> getBookingsByTempleId(String templeId) {
	    return br.findByTempleId(templeId);
	}

	@Override
	public Booking getBookingById(String bookingId) {
		
		return br.findById(bookingId).orElse(null);
	}

	

}
