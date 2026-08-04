package com.priya.ecommerce_platform.controller;

import com.priya.ecommerce_platform.dto.request.ReviewRequest;
import com.priya.ecommerce_platform.dto.response.ApiResponse;
import com.priya.ecommerce_platform.dto.response.ReviewResponse;
import com.priya.ecommerce_platform.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * ==========================================
     * CUSTOMER - ADD REVIEW
     * POST /api/customer/reviews
     * ==========================================
     */
    @PostMapping("/customer/reviews")
    public ApiResponse<ReviewResponse> addReview(
            Authentication authentication,
            @Valid @RequestBody ReviewRequest request
    ) {

        ReviewResponse response = reviewService.addReview(
                authentication.getName(),
                request
        );

        return ApiResponse.success(
                "Review added successfully",
                response
        );
    }

    /**
     * ==========================================
     * CUSTOMER - UPDATE REVIEW
     * PUT /api/customer/reviews/{reviewId}
     * ==========================================
     */
    @PutMapping("/customer/reviews/{reviewId}")
    public ApiResponse<ReviewResponse> updateReview(
            @PathVariable Long reviewId,
            Authentication authentication,
            @Valid @RequestBody ReviewRequest request
    ) {

        ReviewResponse response = reviewService.updateReview(
                reviewId,
                authentication.getName(),
                request
        );

        return ApiResponse.success(
                "Review updated successfully",
                response
        );
    }

    /**
     * ==========================================
     * CUSTOMER - DELETE REVIEW
     * DELETE /api/customer/reviews/{reviewId}
     * ==========================================
     */
    @DeleteMapping("/customer/reviews/{reviewId}")
    public ApiResponse<String> deleteReview(
            @PathVariable Long reviewId,
            Authentication authentication
    ) {

        reviewService.deleteReview(
                reviewId,
                authentication.getName()
        );

        return ApiResponse.success(
                "Review deleted successfully",
                "Deleted"
        );
    }

    /**
     * ==========================================
     * PUBLIC - GET REVIEWS OF PRODUCT
     * GET /api/products/{productId}/reviews
     * ==========================================
     */
    @GetMapping("/products/{productId}/reviews")
    public ApiResponse<List<ReviewResponse>> getProductReviews(
            @PathVariable Long productId
    ) {

        List<ReviewResponse> responses =
                reviewService.getProductReviews(productId);

        return ApiResponse.success(
                "Reviews fetched successfully",
                responses
        );
    }

    /**
     * ==========================================
     * PUBLIC - PRODUCT RATING
     * GET /api/products/{productId}/rating
     * ==========================================
     */
    @GetMapping("/products/{productId}/rating")
    public ApiResponse<Double> getAverageRating(
            @PathVariable Long productId
    ) {

        Double rating =
                reviewService.getAverageRating(productId);

        return ApiResponse.success(
                "Average rating fetched successfully",
                rating
        );
    }
}