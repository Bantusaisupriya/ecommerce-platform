package com.priya.ecommerce_platform.service.impl;

import com.priya.ecommerce_platform.dto.response.*;
import com.priya.ecommerce_platform.entity.Product;
import com.priya.ecommerce_platform.enums.ApplicationStatus;
import com.priya.ecommerce_platform.enums.OrderStatus;
import com.priya.ecommerce_platform.enums.Role;
import com.priya.ecommerce_platform.repository.CategoryRepository;
import com.priya.ecommerce_platform.repository.OrderItemRepository;
import com.priya.ecommerce_platform.repository.OrderRepository;
import com.priya.ecommerce_platform.repository.ProductRepository;
import com.priya.ecommerce_platform.repository.UserRepository;
import com.priya.ecommerce_platform.repository.VendorApplicationRepository;
import com.priya.ecommerce_platform.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final CategoryRepository categoryRepository;
    private final VendorApplicationRepository vendorApplicationRepository;
    @Override
    public AdminDashboardResponse getDashboard() {

        return AdminDashboardResponse.builder()
                .totalUsers(userRepository.getTotalUsers())
                .totalCustomers(userRepository.countByRole(Role.CUSTOMER))
                .totalVendors(userRepository.countByRole(Role.VENDOR))
                .totalProducts(productRepository.getTotalProducts())
                .totalCategories(categoryRepository.getTotalCategories())
                .totalOrders(orderRepository.getTotalOrders())
                .pendingOrders(orderRepository.countByStatus(OrderStatus.PENDING_PAYMENT))
                .shippedOrders(orderRepository.countByStatus(OrderStatus.SHIPPED))
                .deliveredOrders(orderRepository.countByStatus(OrderStatus.DELIVERED))
                .cancelledOrders(orderRepository.countByStatus(OrderStatus.CANCELLED))
                .totalRevenue(orderRepository.getTotalRevenue())
                .pendingVendorApplications(
                        vendorApplicationRepository.countByStatus(ApplicationStatus.PENDING)
                )
                .approvedVendorApplications(
                        vendorApplicationRepository.countByStatus(ApplicationStatus.APPROVED)
                )
                .rejectedVendorApplications(
                        vendorApplicationRepository.countByStatus(ApplicationStatus.REJECTED)
                )
                .build();
    }
    @Override
    public List<MonthlyRevenueResponse> getMonthlyRevenue(Integer year) {

        return orderRepository.getMonthlyRevenue(year)
                .stream()
                .map(obj -> MonthlyRevenueResponse.builder()
                        .year((Integer) obj[0])
                        .month((Integer) obj[1])
                        .totalOrders((Long) obj[2])
                        .revenue((BigDecimal) obj[3])
                        .build())
                .collect(Collectors.toList());
    }
    @Override
    public List<YearlyRevenueResponse> getYearlyRevenue() {

        return orderRepository.getYearlyRevenue()
                .stream()
                .map(obj -> YearlyRevenueResponse.builder()
                        .year((Integer) obj[0])
                        .totalOrders((Long) obj[1])
                        .revenue((BigDecimal) obj[2])
                        .build())
                .collect(Collectors.toList());
    }
    @Override
    public List<TopProductResponse> getTopSellingProducts() {

        return orderItemRepository.getTopSellingProducts()
                .stream()
                .map(obj -> TopProductResponse.builder()
                        .productId(((Number) obj[0]).longValue())
                        .productName((String) obj[1])
                        .totalSold(((Number) obj[2]).longValue())
                        .build())
                .collect(Collectors.toList());
    }
    @Override
    public List<VendorEarningResponse> getVendorEarnings() {

        return orderItemRepository.getVendorRevenue()
                .stream()
                .map(obj -> VendorEarningResponse.builder()
                        .vendorId(((Number) obj[0]).longValue())
                        .vendorName((String) obj[1])
                        .totalRevenue((BigDecimal) obj[2])
                        .build())
                .collect(Collectors.toList());
    }
    @Override
    public List<TopCustomerResponse> getTopCustomers() {

        return orderRepository.getTopCustomers()
                .stream()
                .map(obj -> TopCustomerResponse.builder()
                        .customerId(((Number) obj[0]).longValue())
                        .customerName((String) obj[1])
                        .totalOrders(((Number) obj[2]).longValue())
                        .totalSpent((BigDecimal) obj[3])
                        .build())
                .collect(Collectors.toList());
    }
    @Override
    public List<CategorySalesResponse> getCategorySales() {

        return orderItemRepository.getCategoryRevenue()
                .stream()
                .map(obj -> CategorySalesResponse.builder()
                        .categoryId(((Number) obj[0]).longValue())
                        .categoryName((String) obj[1])
                        .revenue((BigDecimal) obj[2])
                        .build())
                .collect(Collectors.toList());
    }
    @Override
    public List<LowStockResponse> getLowStockProducts() {

        return productRepository.findByStockQuantityLessThan(10)
                .stream()
                .filter(product -> product.getStockQuantity() > 0)
                .map(this::mapLowStockResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<LowStockResponse> getOutOfStockProducts() {

        return productRepository.findByStockQuantity(0)
                .stream()
                .map(this::mapLowStockResponse)
                .collect(Collectors.toList());
    }
    private LowStockResponse mapLowStockResponse(Product product) {

        return LowStockResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .stock(product.getStockQuantity())
                .build();
    }
}