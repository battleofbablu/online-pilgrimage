package com.review.service.controller;



import com.review.service.dto.SentimentResponse;
import com.review.service.service.SentimentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final SentimentService sentimentService;

    @PostMapping("/analyze")
    public SentimentResponse analyze(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String location = payload.get("location");
        String text = payload.get("text");
        return sentimentService.analyzeSentiment(text, username, location);
    }


    @GetMapping
    public List<SentimentResponse> getAllReviews() {
        return sentimentService.getAllReviews();
    }


}




