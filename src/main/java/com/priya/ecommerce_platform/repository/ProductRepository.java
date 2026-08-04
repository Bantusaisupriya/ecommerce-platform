package com.priya.ecommerce_platform.repository;

import com.priya.ecommerce_platform.entity.Product;
import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /*
     * ==========================
     * Vendor Products
     * ==========================
     */

    List<Product> findByVendor(User vendor);

    /*
     * ==========================
     * Active Products
     * ==========================
     */

    List<Product> findByStatus(ProductStatus status);

    /*
     * ==========================
     * Products By Category
     * ==========================
     */

    List<Product> findByCategoryId(Long categoryId);

    /*
     * ==========================
     * Search Products
     * ==========================
     */

    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    /*
     * ==========================
     * SKU
     * ==========================
     */

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);

    /*
     * ==========================
     * Dashboard Statistics
     * ==========================
     */

    long countByStatus(ProductStatus status);

    @Query("""
            SELECT COUNT(p)
            FROM Product p
            """)
    long getTotalProducts();

    /*
     * ==========================
     * Inventory Analytics
     * ==========================
     */

    List<Product> findByStockQuantityLessThan(Integer stockQuantity);

    List<Product> findByStockQuantity(Integer stockQuantity);

    /*
     * Products having stock greater than a value
     */

    List<Product> findByStockQuantityGreaterThan(Integer stockQuantity);

    /*
     * Products by Vendor and Status
     */

    List<Product> findByVendorAndStatus(User vendor,
                                        ProductStatus status);

    /*
     * Count Vendor Products
     */

    long countByVendor(User vendor);

    /*
     * Count Products by Category
     */

    long countByCategoryId(Long categoryId);

}