package com.priya.ecommerce_platform.repository;

import com.priya.ecommerce_platform.entity.Product;
import com.priya.ecommerce_platform.entity.Wishlist;
import com.priya.ecommerce_platform.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface WishlistItemRepository
        extends JpaRepository<WishlistItem,Long> {


    Optional<WishlistItem> findByWishlistAndProduct(
            Wishlist wishlist,
            Product product
    );


    void deleteByWishlistAndProduct(
            Wishlist wishlist,
            Product product
    );

}