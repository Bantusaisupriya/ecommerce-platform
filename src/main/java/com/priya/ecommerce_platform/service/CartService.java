package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.request.AddToCartRequest;
import com.priya.ecommerce_platform.dto.response.CartResponse;

public interface CartService {

    CartResponse addToCart(
            String customerEmail,
            AddToCartRequest request
    );

    CartResponse getCart(
            String customerEmail
    );

    CartResponse updateQuantity(
            Long cartItemId,
            Integer quantity,
            String customerEmail
    );

    void removeItem(
            Long cartItemId,
            String customerEmail
    );

    void clearCart(
            String customerEmail
    );
}