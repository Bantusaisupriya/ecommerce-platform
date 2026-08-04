package com.priya.ecommerce_platform.repository;

import com.priya.ecommerce_platform.entity.OrderItem;
import com.priya.ecommerce_platform.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByProduct(Product product);

    /*
     * Top Selling Products
     */
    @Query("""
            SELECT oi.product.id,
                   oi.product.productName,
                   SUM(oi.quantity)
            FROM OrderItem oi
            GROUP BY oi.product.id, oi.product.productName
            ORDER BY SUM(oi.quantity) DESC
            """)
    List<Object[]> getTopSellingProducts();

    /*
     * Vendor Revenue
     */
    @Query("""
            SELECT oi.product.vendor.id,
                   oi.product.vendor.fullName,
                   SUM(oi.price * oi.quantity)
            FROM OrderItem oi
            GROUP BY oi.product.vendor.id,
                     oi.product.vendor.fullName
            ORDER BY SUM(oi.price * oi.quantity) DESC
            """)
    List<Object[]> getVendorRevenue();

    /*
     * Revenue by Category
     */
    @Query("""
            SELECT oi.product.category.id,
                   oi.product.category.name,
                   SUM(oi.price * oi.quantity)
            FROM OrderItem oi
            GROUP BY oi.product.category.id,
                     oi.product.category.name
            ORDER BY SUM(oi.price * oi.quantity) DESC
            """)
    List<Object[]> getCategoryRevenue();

}