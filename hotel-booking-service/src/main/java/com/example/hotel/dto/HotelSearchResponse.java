package com.example.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelSearchResponse {
    private List<Hotel> hotels;

    @Data
    public static class Hotel {
        private String name;
        private String city;
        private double price;
        private double rating;
        private String imageUrl;
    }
}

