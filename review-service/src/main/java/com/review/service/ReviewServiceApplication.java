package com.review.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */

@SpringBootApplication(scanBasePackages = {
        "com.review.service",
        "com.example.openai.service" // explicitly scan OpenAIService package
})
public class ReviewServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ReviewServiceApplication.class, args);
    }
}
