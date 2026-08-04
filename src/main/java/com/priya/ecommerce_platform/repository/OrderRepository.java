package com.priya.ecommerce_platform.repository;

import com.priya.ecommerce_platform.entity.Order;
import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /*
     * ============================================
     * Customer Orders
     * ============================================
     */

    List<Order> findByCustomer(User customer);

    /*
     * ============================================
     * Orders by Status
     * ============================================
     */

    List<Order> findByStatus(OrderStatus status);

    long countByStatus(OrderStatus status);

    /*
     * ============================================
     * Find Order
     * ============================================
     */

    Optional<Order> findByOrderNumber(String orderNumber);

    /*
     * ============================================
     * Dashboard
     * ============================================
     */

    @Query("""
            SELECT COUNT(o)
            FROM Order o
            """)
    long getTotalOrders();

    @Query("""
            SELECT COALESCE(SUM(o.totalAmount),0)
            FROM Order o
            WHERE o.status = com.priya.ecommerce_platform.enums.OrderStatus.CONFIRMED
            """)
    BigDecimal getTotalRevenue();

    @Query("""
            SELECT COALESCE(AVG(o.totalAmount),0)
            FROM Order o
            WHERE o.status = com.priya.ecommerce_platform.enums.OrderStatus.CONFIRMED
            """)
    BigDecimal getAverageOrderValue();

    /*
     * ============================================
     * Monthly Revenue
     * ============================================
     */

    @Query("""
            SELECT
                YEAR(o.createdAt),
                MONTH(o.createdAt),
                COUNT(o),
                COALESCE(SUM(o.totalAmount),0)
            FROM Order o
            WHERE YEAR(o.createdAt)=:year
              AND o.status = com.priya.ecommerce_platform.enums.OrderStatus.CONFIRMED
            GROUP BY YEAR(o.createdAt), MONTH(o.createdAt)
            ORDER BY MONTH(o.createdAt)
            """)
    List<Object[]> getMonthlyRevenue(@Param("year") Integer year);

    /*
     * ============================================
     * Yearly Revenue
     * ============================================
     */

    @Query("""
            SELECT
                YEAR(o.createdAt),
                COUNT(o),
                COALESCE(SUM(o.totalAmount),0)
            FROM Order o
            WHERE o.status = com.priya.ecommerce_platform.enums.OrderStatus.CONFIRMED
            GROUP BY YEAR(o.createdAt)
            ORDER BY YEAR(o.createdAt)
            """)
    List<Object[]> getYearlyRevenue();

    /*
     * ============================================
     * Top Customers
     * ============================================
     */

    @Query("""
            SELECT
                o.customer.id,
                o.customer.fullName,
                COUNT(o),
                COALESCE(SUM(o.totalAmount),0)
            FROM Order o
            WHERE o.status = com.priya.ecommerce_platform.enums.OrderStatus.CONFIRMED
            GROUP BY
                o.customer.id,
                o.customer.fullName
            ORDER BY
                SUM(o.totalAmount) DESC
            """)
    List<Object[]> getTopCustomers();

    /*
     * ============================================
     * Vendor Orders
     * ============================================
     */

    @Query("""
            SELECT DISTINCT o
            FROM Order o
            JOIN o.orderItems oi
            WHERE oi.product.vendor = :vendor
            ORDER BY o.createdAt DESC
            """)
    List<Order> findVendorOrders(@Param("vendor") User vendor);

}