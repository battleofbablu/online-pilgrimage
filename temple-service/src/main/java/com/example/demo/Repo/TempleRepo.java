package com.example.demo.Repo;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Model.Temple;

public interface TempleRepo extends JpaRepository<Temple,String> 
{
	@Query("SELECT t FROM Temple t WHERE LOWER(t.temple_name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(t.location) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Temple> searchTemples(@Param("query") String query);
} 
