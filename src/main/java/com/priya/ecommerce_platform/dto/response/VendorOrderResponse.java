package com.priya.ecommerce_platform.dto.response;

import com.priya.ecommerce_platform.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorOrderResponse {

    private Long orderId;

    private String orderNumber;

    private String customerName;

    private String customerEmail;

    /**
     * Total amount for this vendor's products
     * in the order.
     */
    private BigDecimal totalAmount;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;

    private String message;

}