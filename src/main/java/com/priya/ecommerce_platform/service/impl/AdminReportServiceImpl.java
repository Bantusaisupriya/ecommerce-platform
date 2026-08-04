package com.priya.ecommerce_platform.service.impl;

import com.priya.ecommerce_platform.dto.response.AdminReportResponse;
import com.priya.ecommerce_platform.entity.Order;
import com.priya.ecommerce_platform.enums.OrderStatus;
import com.priya.ecommerce_platform.repository.OrderRepository;
import com.priya.ecommerce_platform.service.AdminReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminReportServiceImpl implements AdminReportService {

    private final OrderRepository orderRepository;

    @Override
    public AdminReportResponse getSalesReport() {

        List<Order> orders = orderRepository.findAll();

        long totalOrders = (long) orders.size();

        long pendingOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.PENDING)
                .count();

        long confirmedOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.CONFIRMED)
                .count();

        long processingOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.PROCESSING)
                .count();

        long shippedOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.SHIPPED)
                .count();

        long deliveredOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .count();

        long cancelledOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.CANCELLED)
                .count();

        BigDecimal totalRevenue = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageOrderValue = BigDecimal.ZERO;

        if (totalOrders > 0) {
            averageOrderValue = totalRevenue.divide(
                    BigDecimal.valueOf(totalOrders),
                    2,
                    RoundingMode.HALF_UP
            );
        }

        return AdminReportResponse.builder()
                .totalOrders(totalOrders)
                .pendingOrders(pendingOrders)
                .confirmedOrders(confirmedOrders)
                .processingOrders(processingOrders)
                .shippedOrders(shippedOrders)
                .deliveredOrders(deliveredOrders)
                .cancelledOrders(cancelledOrders)
                .totalRevenue(totalRevenue)
                .averageOrderValue(averageOrderValue)
                .build();
    }
}