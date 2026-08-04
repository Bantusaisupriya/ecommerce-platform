package com.priya.ecommerce_platform.repository;

import com.priya.ecommerce_platform.entity.Cart;
import com.priya.ecommerce_platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
}