package com.review.service.service;

import com.review.service.dto.SentimentResponse;
import com.review.service.entity.Review;
import com.review.service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SentimentService {
    private final ReviewRepository reviewRepository;

    public SentimentResponse analyzeSentiment(String text, String username, String location) {
        // 📝 Dummy sentiment logic (replace with your AI/NLP logic)
        String sentiment = text.contains("good") ? "positive" : "neutral";
        double score = sentiment.equals("positive") ? 0.8 : 0.5;

        Review review = Review.builder()
                .username(username)
                .location(location)
                .text(text)
                .sentiment(sentiment)
                .score(score)
                .build();

        reviewRepository.save(review);

        return new SentimentResponse(review.getId(), text, sentiment, score);
    }

    public List<SentimentResponse> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(r -> new SentimentResponse(r.getId(), r.getText(), r.getSentiment(), r.getScore()))
                .collect(Collectors.toList());
    }
}
