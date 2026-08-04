package com.priya.ecommerce_platform.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesAnalyticsResponse {

    private Long totalOrders;

    private Long completedOrders;

    private Long cancelledOrders;

    private BigDecimal totalRevenue;

    private BigDecimal averageOrderValue;

    private Long totalCustomers;

    private Long totalVendors;

    private Long totalProducts;

}