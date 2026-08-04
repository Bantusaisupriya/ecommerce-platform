package com.priya.ecommerce_platform.repository;

import com.priya.ecommerce_platform.entity.Product;
import com.priya.ecommerce_platform.entity.Review;
import com.priya.ecommerce_platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Get all reviews of a product
     */
    List<Review> findByProduct(Product product);

    /**
     * Get all reviews written by a customer
     */
    List<Review> findByCustomer(User customer);

    /**
     * Check whether customer has already reviewed the product
     */
    Optional<Review> findByProductAndCustomer(
            Product product,
            User customer
    );

    /**
     * Average rating of a product
     */
    @Query("""
            SELECT AVG(r.rating)
            FROM Review r
            WHERE r.product.id = :productId
            """)
    Double getAverageRating(Long productId);

    /**
     * Total number of reviews of a product
     */
    long countByProduct(Product product);

    /**
     * Delete review by customer and product
     */
    void deleteByProductAndCustomer(
            Product product,
            User customer
    );

}