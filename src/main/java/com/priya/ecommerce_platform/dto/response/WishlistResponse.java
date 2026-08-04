package com.priya.ecommerce_platform.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistResponse {


    private Long wishlistId;


    private List<WishlistProductResponse> products;



    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class WishlistProductResponse {


        private Long productId;


        private String productName;


        private String brand;


        private BigDecimal price;


        private String imageUrl;

    }

}