package com.priya.ecommerce_platform.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyRevenueResponse {

    private Integer year;

    private Integer month;

    private Long totalOrders;

    private BigDecimal revenue;

}