package com.example.hotel.dto;

import lombok.Data;

import java.util.List;

@Data
public class HotelSearchRequest {
    private String city;
    private List<Integer> amenityIds;      // Optional
    private List<String> neighborhoodIds;  // Optional
}
