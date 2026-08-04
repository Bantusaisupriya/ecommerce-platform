package com.priya.ecommerce_platform.repository;

import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface WishlistRepository
        extends JpaRepository<Wishlist,Long> {


    Optional<Wishlist> findByUser(User user);

}