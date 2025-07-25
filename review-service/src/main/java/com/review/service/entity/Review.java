package com.review.service.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String location;

    @Column(length = 1000)
    private String text;

    private String sentiment;
    private double score;
}
