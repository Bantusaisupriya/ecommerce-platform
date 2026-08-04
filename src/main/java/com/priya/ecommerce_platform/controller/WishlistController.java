package com.priya.ecommerce_platform.controller;


import com.priya.ecommerce_platform.dto.response.ApiResponse;
import com.priya.ecommerce_platform.dto.response.WishlistResponse;
import com.priya.ecommerce_platform.service.WishlistService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/customer/wishlist")
@RequiredArgsConstructor
public class WishlistController {


    private final WishlistService wishlistService;



    /**
     * Add product to wishlist
     *
     * POST
     * /api/customer/wishlist/add/{productId}
     */
    @PostMapping("/add/{productId}")
    public ApiResponse<WishlistResponse> addToWishlist(
            @PathVariable Long productId,
            Authentication authentication
    ) {


        WishlistResponse response =
                wishlistService.addProductToWishlist(
                        authentication.getName(),
                        productId
                );


        return ApiResponse.success(
                "Product added to wishlist",
                response
        );

    }





    /**
     * Get customer wishlist
     *
     * GET
     * /api/customer/wishlist
     */
    @GetMapping
    public ApiResponse<WishlistResponse> getWishlist(
            Authentication authentication
    ) {


        WishlistResponse response =
                wishlistService.getWishlist(
                        authentication.getName()
                );


        return ApiResponse.success(
                "Wishlist fetched successfully",
                response
        );

    }





    /**
     * Remove product from wishlist
     *
     * DELETE
     * /api/customer/wishlist/remove/{productId}
     */
    @DeleteMapping("/remove/{productId}")
    public ApiResponse<String> removeFromWishlist(
            @PathVariable Long productId,
            Authentication authentication
    ) {


        wishlistService.removeProductFromWishlist(
                authentication.getName(),
                productId
        );


        return ApiResponse.success(
                "Product removed from wishlist",
                "Removed"
        );

    }





    /**
     * Clear wishlist
     *
     * DELETE
     * /api/customer/wishlist/clear
     */
    @DeleteMapping("/clear")
    public ApiResponse<String> clearWishlist(
            Authentication authentication
    ) {


        wishlistService.clearWishlist(
                authentication.getName()
        );


        return ApiResponse.success(
                "Wishlist cleared successfully",
                "Cleared"
        );

    }

}