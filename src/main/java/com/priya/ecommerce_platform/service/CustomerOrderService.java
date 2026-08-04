package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.response.OrderResponse;

import java.util.List;

public interface CustomerOrderService {

    List<OrderResponse> getCustomerOrders(String email);

    OrderResponse getCustomerOrderById(
            Long orderId,
            String email
    );

    OrderResponse cancelOrder(
            Long orderId,
            String email
    );
}