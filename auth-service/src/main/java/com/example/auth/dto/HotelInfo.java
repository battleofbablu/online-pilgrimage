package com.example.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelInfo {
    private String name;
    private String addressLine1;
    private String addressLine2;
    private double latitude;
    private double longitude;
}
