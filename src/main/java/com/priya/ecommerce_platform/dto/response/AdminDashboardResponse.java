package com.priya.ecommerce_platform.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDashboardResponse {

    private Long totalUsers;

    private Long totalCustomers;

    private Long totalVendors;

    private Long totalProducts;

    private Long totalCategories;

    private Long totalOrders;

    private Long pendingOrders;

    private Long shippedOrders;

    private Long deliveredOrders;

    private Long cancelledOrders;

    private BigDecimal totalRevenue;

    private Long pendingVendorApplications;

    private Long approvedVendorApplications;

    private Long rejectedVendorApplications;
}