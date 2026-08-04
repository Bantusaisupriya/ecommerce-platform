package com.priya.ecommerce_platform.dto.response;


import com.priya.ecommerce_platform.enums.DiscountType;

import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponResponse {



    private Long id;



    private String code;



    private String description;



    private DiscountType discountType;



    private BigDecimal discountValue;



    private BigDecimal minimumAmount;



    private BigDecimal maximumDiscount;



    private Integer usageLimit;



    private Integer usedCount;



    private boolean active;



    private LocalDateTime expiryDate;



    private LocalDateTime createdAt;


}