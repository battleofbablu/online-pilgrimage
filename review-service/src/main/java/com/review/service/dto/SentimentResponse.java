package com.review.service.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor // ✅ This adds a constructor with all fields
public class SentimentResponse {
    private Long id;
    private String text;
    private String sentiment;
    private double score;
}

