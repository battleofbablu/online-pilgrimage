package com.example.demo.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Temple;
import com.example.demo.Service.TempleService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/temples")

public class TempleController
{
	@Autowired
	TempleService ts;

	 @PostMapping("/register")
	    public ResponseEntity<Temple> registerTemple(@RequestBody Temple t1) {

	        return ts.registerTemple(t1);
	    }

	 @GetMapping("/all")
	    public List<Temple> getAllTemples() {
	        return ts.getAllTemples();
	    }

	 @GetMapping("/fetchsingletemple/{temple_id}")
	    public Temple getTempleById(@PathVariable String temple_id) {
	        return ts.getTempleById(temple_id);
	    }

	 @GetMapping("/search")
	 public List<Temple> searchTemples(@RequestParam String query) {
	     return ts.searchTemples(query);
	 }
}
