package com.sparta.bunga6.review.controller;

import com.sparta.bunga6.review.entity.Review;
import com.sparta.bunga6.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping // 생성
    public Review createReview(@RequestParam Long productId, @RequestParam Long userId, @RequestParam String content) {
        return reviewService.createReview(productId, userId, content);
    }

    @GetMapping // 전체 조회
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/product/{productId}") // 단건 조회
    public List<Review> getReviewsByProductId(@PathVariable Long productId) {
        return reviewService.getReviewsByProductId(productId);
    }

    @PutMapping("/{reviewId}") // 수정
    public Review updateReview(@PathVariable Long reviewId, @RequestParam String content) {
        return reviewService.updateReview(reviewId, content);
    }

    @DeleteMapping("/{reviewId}") // 삭제
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}