package com.priya.ecommerce_platform.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorySalesResponse {

    private Long categoryId;

    private String categoryName;

    private BigDecimal revenue;

}