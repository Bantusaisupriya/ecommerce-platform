package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.request.PlaceOrderRequest;
import com.priya.ecommerce_platform.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(
            String customerEmail,
            PlaceOrderRequest request
    );

    List<OrderResponse> getMyOrders(
            String customerEmail
    );

    OrderResponse getOrderById(
            Long orderId,
            String customerEmail
    );

    void cancelOrder(
            Long orderId,
            String customerEmail
    );
}