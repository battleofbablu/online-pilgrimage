package com.example.demo.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Temple;
import com.example.demo.Repo.TempleRepo;
import com.example.demo.Service.TempleService;

@Service
public class TempleDao implements TempleService 
{
	@Autowired
	TempleRepo tr;

	@Override
	public ResponseEntity<Temple> registerTemple(Temple t1) {
		Temple savedTemple = tr.save(t1);
        return ResponseEntity.ok(savedTemple);
		
	}	

	@Override
	public List<Temple> getAllTemples() {
		
		return tr.findAll();
	}

	@Override
	public Temple getTempleById(String templeId) {
		
		return tr.findById(templeId).orElse(null);
	}

	@Override
	public List<Temple> searchTemples(String query) {
		return tr.searchTemples(query);
	}


	@Override
	public Temple save(Temple temple) {
		return tr.save(temple);
	}
	@Override
	public List<Temple> saveAll(List<Temple> temples) {
		return tr.saveAll(temples);
	}
}
