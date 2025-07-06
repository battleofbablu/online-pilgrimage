package com.example.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RapidHotelDTO {
    private String name;
    private String address;
    private String price;
    private String imageUrl;
}
