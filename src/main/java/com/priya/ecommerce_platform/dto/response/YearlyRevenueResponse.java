package com.priya.ecommerce_platform.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YearlyRevenueResponse {

    private Integer year;

    private Long totalOrders;

    private BigDecimal revenue;

}