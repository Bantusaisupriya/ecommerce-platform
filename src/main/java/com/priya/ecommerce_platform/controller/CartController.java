package com.priya.ecommerce_platform.controller;

import com.priya.ecommerce_platform.dto.request.AddToCartRequest;
import com.priya.ecommerce_platform.dto.request.UpdateCartRequest;
import com.priya.ecommerce_platform.dto.response.ApiResponse;
import com.priya.ecommerce_platform.dto.response.CartResponse;
import com.priya.ecommerce_platform.service.CartService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/customer/cart")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;



    @PostMapping("/add")
    public ApiResponse<CartResponse> addToCart(
            Authentication authentication,
            @Valid @RequestBody AddToCartRequest request) {


        CartResponse response =
                cartService.addToCart(
                        authentication.getName(),
                        request
                );


        return ApiResponse.success(
                "Product added to cart successfully",
                response
        );
    }



    @GetMapping
    public ApiResponse<CartResponse> getCart(
            Authentication authentication) {


        CartResponse response =
                cartService.getCart(
                        authentication.getName()
                );


        return ApiResponse.success(
                "Cart fetched successfully",
                response
        );
    }



    @PutMapping("/{cartItemId}")
    public ApiResponse<CartResponse> updateQuantity(

            @PathVariable Long cartItemId,

            @Valid
            @RequestBody UpdateCartRequest request,

            Authentication authentication
    ) {


        CartResponse response =
                cartService.updateQuantity(
                        cartItemId,
                        request.getQuantity(),
                        authentication.getName()
                );


        return ApiResponse.success(
                "Cart updated successfully",
                response
        );
    }



    @DeleteMapping("/{cartItemId}")
    public ApiResponse<String> removeItem(

            @PathVariable Long cartItemId,

            Authentication authentication
    ) {


        cartService.removeItem(
                cartItemId,
                authentication.getName()
        );


        return ApiResponse.success(
                "Item removed from cart",
                "SUCCESS"
        );
    }



    @DeleteMapping("/clear")
    public ApiResponse<String> clearCart(

            Authentication authentication
    ) {


        cartService.clearCart(
                authentication.getName()
        );


        return ApiResponse.success(
                "Cart cleared successfully",
                "SUCCESS"
        );
    }

}