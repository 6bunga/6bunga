package com.sparta.bunga6.review.service;

import com.sparta.bunga6.product.entity.Product;
import com.sparta.bunga6.product.repository.ProductRepository;
import com.sparta.bunga6.review.entity.Review;
import com.sparta.bunga6.review.repository.ReviewRepository;
import com.sparta.bunga6.user.entity.User;
import com.sparta.bunga6.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Review createReview(Long productId, Long userId, String content) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(userId);

        if (product.isPresent() && user.isPresent()) {
            Review review = new Review();
            review.setProduct(product.get());
            review.setUser(user.get());
            review.setContent(content);
            return reviewRepository.save(review);
        } else {
            throw new IllegalArgumentException("상품 정보를 찾을 수 없습니다.");
        }
    }

    public List<Review> getReviewsByProductId(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            return reviewRepository.findByProduct(product.get());
        } else {
            throw new IllegalArgumentException("상품 정보를 찾을 수 없습니다.");
        }
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review updateReview(Long reviewId, String content) {
        Optional<Review> reviewOpt = reviewRepository.findById(reviewId);
        if (reviewOpt.isPresent()) {
            Review review = reviewOpt.get();
            review.setContent(content);
            return reviewRepository.save(review);
        } else {
            throw new IllegalArgumentException("리뷰를 찾을 수 없습니다.");
        }
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}