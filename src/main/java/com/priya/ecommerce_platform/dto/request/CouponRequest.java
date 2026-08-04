package com.priya.ecommerce_platform.dto.request;


import com.priya.ecommerce_platform.enums.DiscountType;

import jakarta.validation.constraints.*;

import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponRequest {



    @NotBlank(message = "Coupon code is required")
    private String code;



    @NotBlank(message = "Description is required")
    private String description;



    @NotNull(message = "Discount type is required")
    private DiscountType discountType;



    @NotNull(message = "Discount value is required")
    @DecimalMin(
            value = "0.0",
            message = "Discount value must be positive"
    )
    private BigDecimal discountValue;



    private BigDecimal minimumAmount;



    private BigDecimal maximumDiscount;



    @NotNull(message = "Usage limit is required")
    private Integer usageLimit;



    private LocalDateTime expiryDate;


}