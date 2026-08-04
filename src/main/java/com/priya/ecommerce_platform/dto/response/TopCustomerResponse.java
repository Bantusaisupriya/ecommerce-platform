package com.priya.ecommerce_platform.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopCustomerResponse {

    private Long customerId;

    private String customerName;

    private Long totalOrders;

    private BigDecimal totalSpent;

}