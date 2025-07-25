package com.example.hotelmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve images from the local filesystem
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/Users/vijaymadhukarjadhav/workspace/online-pilgriamge-microserivce/uploads/");

    }

    // Example: Allow GET requests for uploads
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/uploads/**").allowedOrigins("http://localhost:4200");
    }


}
