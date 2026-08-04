package com.priya.ecommerce_platform.repository;

import com.priya.ecommerce_platform.entity.Cart;
import com.priya.ecommerce_platform.entity.CartItem;
import com.priya.ecommerce_platform.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    List<CartItem> findByCart(Cart cart);
}