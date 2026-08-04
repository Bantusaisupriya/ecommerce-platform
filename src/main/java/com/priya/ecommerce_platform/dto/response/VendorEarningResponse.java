package com.priya.ecommerce_platform.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorEarningResponse {

    private Long vendorId;

    private String vendorName;

    private BigDecimal totalRevenue;

}