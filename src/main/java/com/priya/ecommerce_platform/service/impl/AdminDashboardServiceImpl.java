package com.priya.ecommerce_platform.service.impl;

import com.priya.ecommerce_platform.dto.response.AdminDashboardResponse;
import com.priya.ecommerce_platform.entity.Order;
import com.priya.ecommerce_platform.enums.ApplicationStatus;
import com.priya.ecommerce_platform.enums.Role;
import com.priya.ecommerce_platform.repository.CategoryRepository;
import com.priya.ecommerce_platform.repository.OrderRepository;
import com.priya.ecommerce_platform.repository.ProductRepository;
import com.priya.ecommerce_platform.repository.UserRepository;
import com.priya.ecommerce_platform.repository.VendorApplicationRepository;
import com.priya.ecommerce_platform.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final VendorApplicationRepository vendorApplicationRepository;

    @Override
    public AdminDashboardResponse getDashboard() {

        BigDecimal revenue = orderRepository.findAll()
                .stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return AdminDashboardResponse.builder()
                .totalUsers(userRepository.count())
                .totalCustomers(userRepository.countByRole(Role.CUSTOMER))
                .totalVendors(userRepository.countByRole(Role.VENDOR))
                .totalProducts(productRepository.count())
                .totalCategories(categoryRepository.count())
                .totalOrders(orderRepository.count())
                .totalRevenue(revenue)
                .pendingVendorApplications(
                        vendorApplicationRepository.countByStatus(ApplicationStatus.PENDING)
                )
                .build();
    }
}