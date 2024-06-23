package com.sparta.bunga6.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {

    private Long id;
    private Long productId;
    private Long userId;
    private String content;

    public ReviewResponse(Long id, Long productId, Long userId, String content) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.content = content;
    }
}