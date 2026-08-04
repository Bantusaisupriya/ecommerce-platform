package com.priya.ecommerce_platform.service.impl;

import com.priya.ecommerce_platform.dto.request.AddToCartRequest;
import com.priya.ecommerce_platform.dto.response.CartResponse;
import com.priya.ecommerce_platform.entity.Cart;
import com.priya.ecommerce_platform.entity.CartItem;
import com.priya.ecommerce_platform.entity.Product;
import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.mapper.CartMapper;
import com.priya.ecommerce_platform.repository.CartItemRepository;
import com.priya.ecommerce_platform.repository.CartRepository;
import com.priya.ecommerce_platform.repository.ProductRepository;
import com.priya.ecommerce_platform.repository.UserRepository;
import com.priya.ecommerce_platform.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    @Override
    public CartResponse addToCart(String customerEmail,
                                  AddToCartRequest request) {

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUser(customer)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(customer)
                            .cartItems(new ArrayList<>())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();

                    return cartRepository.save(newCart);
                });

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem == null) {

            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();

        } else {

            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity()
            );
        }

        cartItemRepository.save(cartItem);

        return getCart(customerEmail);
    }

    @Override
    public CartResponse getCart(String customerEmail) {

        System.out.println("===== GET CART =====");

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartRepository.findByUser(customer)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        System.out.println("Cart ID : " + cart.getId());
        System.out.println("Items   : " + cart.getCartItems().size());

        CartResponse response = cartMapper.toCartResponse(cart);

        System.out.println("Response : " + response);

        return response;
    }

    @Override
    public CartResponse updateQuantity(Long cartItemId,
                                       Integer quantity,
                                       String customerEmail) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        return getCart(customerEmail);
    }

    @Override
    public void removeItem(Long cartItemId,
                           String customerEmail) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart(String customerEmail) {

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartRepository.findByUser(customer)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getCartItems().clear();

        cartRepository.save(cart);
    }
}  