package com.priya.ecommerce_platform.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

    private Long reviewId;

    private Long productId;

    private String productName;

    private Long customerId;

    private String customerName;

    private Integer rating;

    private String review;

    private LocalDateTime createdAt;

}