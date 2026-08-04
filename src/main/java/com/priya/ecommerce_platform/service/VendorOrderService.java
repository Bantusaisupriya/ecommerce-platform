package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.request.UpdateOrderStatusRequest;
import com.priya.ecommerce_platform.dto.response.VendorOrderResponse;

import java.util.List;

public interface VendorOrderService {

    List<VendorOrderResponse> getVendorOrders(
            String vendorEmail
    );

    VendorOrderResponse updateOrderStatus(
            Long orderId,
            String vendorEmail,
            UpdateOrderStatusRequest request
    );

}