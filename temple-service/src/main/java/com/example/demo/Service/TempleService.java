package com.example.demo.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.Model.Temple;

public interface TempleService
{
	 public ResponseEntity<Temple> registerTemple(Temple t1);
	 public List<Temple> getAllTemples();
	 public Temple getTempleById(String templeId);
	 public List<Temple> searchTemples(String query);
	// ✅ Save one temple (used in Wikipedia import)
	Temple save(Temple temple);

	// ✅ Save all temples (bulk import)
	List<Temple> saveAll(List<Temple> temples);

}
