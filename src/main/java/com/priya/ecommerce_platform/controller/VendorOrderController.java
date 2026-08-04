package com.priya.ecommerce_platform.controller;

import com.priya.ecommerce_platform.dto.request.UpdateOrderStatusRequest;
import com.priya.ecommerce_platform.dto.response.ApiResponse;
import com.priya.ecommerce_platform.dto.response.VendorOrderResponse;
import com.priya.ecommerce_platform.service.VendorOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor/orders")
@RequiredArgsConstructor
public class VendorOrderController {

    private final VendorOrderService vendorOrderService;


    @GetMapping
    public ApiResponse<List<VendorOrderResponse>> getVendorOrders(
            Authentication authentication) {

        List<VendorOrderResponse> response =
                vendorOrderService.getVendorOrders(
                        authentication.getName()
                );

        return ApiResponse.success(
                "Vendor orders fetched successfully",
                response
        );
    }


    @PutMapping("/{orderId}/status")
    public ApiResponse<VendorOrderResponse> updateOrderStatus(
            @PathVariable Long orderId,
            Authentication authentication,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        VendorOrderResponse response =
                vendorOrderService.updateOrderStatus(
                        orderId,
                        authentication.getName(),
                        request
                );

        return ApiResponse.success(
                "Order status updated successfully",
                response
        );
    }
}