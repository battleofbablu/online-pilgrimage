package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Booking;
import com.example.demo.Service.BookingService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/bookings")

public class BookingController 
{
	@Autowired
	BookingService bs;
	
	@PostMapping("/create")
	public ResponseEntity<Booking> createBooking(@RequestBody Booking b1){
		return bs.registerBooking(b1);
	}
	
	@GetMapping("/all")
	public List<Booking> getAllBookings(){
		return bs.getAllBookings();
	}
	
	@GetMapping("/temple/{temple_id}")
	public List<Booking> getBookingsByTemple(@PathVariable String templeId){
		return bs.getBookingsByTempleId(templeId);
	}
	
	
}
