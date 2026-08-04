package com.priya.ecommerce_platform.controller;

import com.priya.ecommerce_platform.dto.response.ApiResponse;
import com.priya.ecommerce_platform.dto.response.OrderResponse;
import com.priya.ecommerce_platform.service.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/orders")
@RequiredArgsConstructor
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    @GetMapping
    public ApiResponse<List<OrderResponse>> getMyOrders(
            Authentication authentication
    ) {

        return ApiResponse.success(
                "Customer orders fetched successfully",
                customerOrderService.getCustomerOrders(
                        authentication.getName()
                )
        );
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrderById(
            @PathVariable Long orderId,
            Authentication authentication
    ) {

        return ApiResponse.success(
                "Order fetched successfully",
                customerOrderService.getCustomerOrderById(
                        orderId,
                        authentication.getName()
                )
        );
    }

    @PutMapping("/{orderId}/cancel")
    public ApiResponse<OrderResponse> cancelOrder(
            @PathVariable Long orderId,
            Authentication authentication
    ) {

        return ApiResponse.success(
                "Order cancelled successfully",
                customerOrderService.cancelOrder(
                        orderId,
                        authentication.getName()
                )
        );
    }
}