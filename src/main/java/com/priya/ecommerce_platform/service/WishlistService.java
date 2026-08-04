package com.priya.ecommerce_platform.service;


import com.priya.ecommerce_platform.dto.response.WishlistResponse;


public interface WishlistService {


    WishlistResponse addProductToWishlist(
            String email,
            Long productId
    );


    WishlistResponse getWishlist(
            String email
    );


    void removeProductFromWishlist(
            String email,
            Long productId
    );


    void clearWishlist(
            String email
    );

}