package com.priya.ecommerce_platform.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopProductResponse {

    private Long productId;

    private String productName;

    private Long totalSold;

}