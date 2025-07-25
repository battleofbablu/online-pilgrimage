package com.example.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class HotelBookingServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(HotelBookingServiceApplication.class, args);
    }
}
