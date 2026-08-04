package com.priya.ecommerce_platform.dto.response;

import com.priya.ecommerce_platform.enums.PaymentMethod;
import com.priya.ecommerce_platform.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long paymentId;

    private Long orderId;

    private String orderNumber;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    // Razorpay Order ID
    private String razorpayOrderId;

    // Amount returned to frontend (paise)
    private Integer razorpayAmount;

    // Currency
    private String currency;

    // Key ID required by frontend
    private String key;

    private String message;
}