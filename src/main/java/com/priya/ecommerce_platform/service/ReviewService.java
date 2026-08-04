package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.request.ReviewRequest;
import com.priya.ecommerce_platform.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    /**
     * Add a new review
     */
    ReviewResponse addReview(
            String customerEmail,
            ReviewRequest request
    );

    /**
     * Update an existing review
     */
    ReviewResponse updateReview(
            Long reviewId,
            String customerEmail,
            ReviewRequest request
    );

    /**
     * Delete a review
     */
    void deleteReview(
            Long reviewId,
            String customerEmail
    );

    /**
     * Get all reviews for a product
     */
    List<ReviewResponse> getProductReviews(
            Long productId
    );

    /**
     * Get a customer's own reviews
     */
    List<ReviewResponse> getMyReviews(
            String customerEmail
    );

    /**
     * Get average rating for a product
     */
    Double getAverageRating(
            Long productId
    );

    /**
     * Get total review count for a product
     */
    Long getReviewCount(
            Long productId
    );
}