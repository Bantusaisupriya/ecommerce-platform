package com.priya.ecommerce_platform.controller;

import com.priya.ecommerce_platform.dto.response.ApiResponse;
import com.priya.ecommerce_platform.dto.response.OrderResponse;
import com.priya.ecommerce_platform.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrderById(
            @PathVariable Long orderId,
            Authentication authentication) {

        OrderResponse response =
                orderService.getOrderById(
                        orderId,
                        authentication.getName()
                );

        return ApiResponse.success(
                "Order fetched successfully",
                response
        );
    }


}