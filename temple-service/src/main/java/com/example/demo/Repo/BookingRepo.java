package com.example.demo.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Model.Booking;

public interface BookingRepo extends JpaRepository<Booking, String>
{
	 @Query("SELECT b FROM Booking b WHERE b.temple_id = :templeId")
	    List<Booking> findByTempleId(@Param("templeId") String templeId);
}
