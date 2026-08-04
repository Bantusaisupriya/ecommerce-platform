package com.priya.ecommerce_platform.repository;

import com.priya.ecommerce_platform.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    /*
     * Find Category by Name
     */
    Optional<Category> findByName(String name);

    /*
     * Check Category Exists
     */
    boolean existsByName(String name);

    /*
     * Total Categories
     */
    @Query("""
            SELECT COUNT(c)
            FROM Category c
            """)
    long getTotalCategories();

}