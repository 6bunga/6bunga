package com.sparta.bunga6.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String content;

    public Review(Long productId, Long userId, String content) {
        this.productId = productId;
        this.userId = userId;
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}