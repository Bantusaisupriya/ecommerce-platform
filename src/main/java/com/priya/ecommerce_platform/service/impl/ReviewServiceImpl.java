package com.priya.ecommerce_platform.service.impl;

import com.priya.ecommerce_platform.dto.request.ReviewRequest;
import com.priya.ecommerce_platform.dto.response.ReviewResponse;
import com.priya.ecommerce_platform.entity.Product;
import com.priya.ecommerce_platform.entity.Review;
import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.repository.ProductRepository;
import com.priya.ecommerce_platform.repository.ReviewRepository;
import com.priya.ecommerce_platform.repository.UserRepository;
import com.priya.ecommerce_platform.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * Add Review
     */
    @Override
    public ReviewResponse addReview(
            String customerEmail,
            ReviewRequest request
    ) {

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        // Check duplicate review
        if (reviewRepository.findByProductAndCustomer(product, customer).isPresent()) {
            throw new RuntimeException(
                    "You have already reviewed this product"
            );
        }

        Review review = Review.builder()
                .product(product)
                .customer(customer)
                .rating(request.getRating())
                .review(request.getReview())
                .build();

        review = reviewRepository.save(review);

        return mapToResponse(review);
    }
    /**
     * Update Review
     */
    @Override
    public ReviewResponse updateReview(
            Long reviewId,
            String customerEmail,
            ReviewRequest request
    ) {

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new RuntimeException("Review not found"));

        // Only the review owner can update
        if (!review.getCustomer().getId().equals(customer.getId())) {
            throw new RuntimeException(
                    "You are not authorized to update this review"
            );
        }

        // If changing product, check it exists
        if (!review.getProduct().getId().equals(request.getProductId())) {

            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException("Product not found"));

            // Prevent duplicate review for the new product
            reviewRepository.findByProductAndCustomer(product, customer)
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(reviewId)) {
                            throw new RuntimeException(
                                    "You have already reviewed this product"
                            );
                        }
                    });

            review.setProduct(product);
        }

        review.setRating(request.getRating());
        review.setReview(request.getReview());

        review = reviewRepository.save(review);

        return mapToResponse(review);
    }

    /**
     * Delete Review
     */
    @Override
    public void deleteReview(
            Long reviewId,
            String customerEmail
    ) {

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new RuntimeException("Review not found"));

        // Only the review owner can delete
        if (!review.getCustomer().getId().equals(customer.getId())) {
            throw new RuntimeException(
                    "You are not authorized to delete this review"
            );
        }

        reviewRepository.delete(review);
    }
    /**
     * Get all reviews of a product
     */
    @Override
    public List<ReviewResponse> getProductReviews(
            Long productId
    ) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        return reviewRepository.findByProduct(product)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Get logged-in customer's reviews
     */
    @Override
    public List<ReviewResponse> getMyReviews(
            String customerEmail
    ) {

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        return reviewRepository.findByCustomer(customer)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Get average rating of a product
     */
    @Override
    public Double getAverageRating(
            Long productId
    ) {

        Double rating = reviewRepository.getAverageRating(productId);

        return rating == null ? 0.0 : rating;
    }

    /**
     * Get total review count
     */
    @Override
    public Long getReviewCount(
            Long productId
    ) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        return reviewRepository.countByProduct(product);
    }

    /**
     * Convert Entity to Response DTO
     */
    private ReviewResponse mapToResponse(
            Review review
    ) {

        return ReviewResponse.builder()
                .reviewId(review.getId())
                .productId(review.getProduct().getId())
                .productName(review.getProduct().getProductName())
                .customerId(review.getCustomer().getId())
                .customerName(review.getCustomer().getFullName())
                .rating(review.getRating())
                .review(review.getReview())
                .createdAt(review.getCreatedAt())
                .build();
    }

}