package com.priya.ecommerce_platform.mapper;

import com.priya.ecommerce_platform.dto.response.CartItemResponse;
import com.priya.ecommerce_platform.dto.response.CartResponse;
import com.priya.ecommerce_platform.entity.Cart;
import com.priya.ecommerce_platform.entity.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartResponse toCartResponse(Cart cart) {

        List<CartItemResponse> items = cart.getCartItems()
                .stream()
                .map(this::toCartItemResponse)
                .collect(Collectors.toList());

        BigDecimal grandTotal = items.stream()
                .map(CartItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .cartId(cart.getId())
                .items(items)
                .grandTotal(grandTotal)
                .build();
    }

    public CartItemResponse toCartItemResponse(CartItem cartItem) {

        BigDecimal totalPrice =
                cartItem.getProduct()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        return CartItemResponse.builder()
                .cartItemId(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .productName(cartItem.getProduct().getProductName())
                .price(cartItem.getProduct().getPrice())
                .quantity(cartItem.getQuantity())
                .totalPrice(totalPrice)
                .build();
    }
}