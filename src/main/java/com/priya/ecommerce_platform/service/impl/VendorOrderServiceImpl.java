package com.priya.ecommerce_platform.service.impl;

import com.priya.ecommerce_platform.dto.request.UpdateOrderStatusRequest;
import com.priya.ecommerce_platform.dto.response.OrderItemResponse;
import com.priya.ecommerce_platform.dto.response.VendorOrderResponse;
import com.priya.ecommerce_platform.entity.Order;
import com.priya.ecommerce_platform.entity.OrderItem;
import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.repository.OrderRepository;
import com.priya.ecommerce_platform.repository.UserRepository;
import com.priya.ecommerce_platform.service.VendorOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorOrderServiceImpl implements VendorOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public List<VendorOrderResponse> getVendorOrders(String vendorEmail) {

        User vendor = userRepository.findByEmail(vendorEmail)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        List<Order> allOrders = orderRepository.findAll();

        List<VendorOrderResponse> responses = new ArrayList<>();

        for (Order order : allOrders) {

            List<OrderItemResponse> items = new ArrayList<>();
            BigDecimal vendorTotal = BigDecimal.ZERO;

            for (OrderItem item : order.getOrderItems()) {

                if (item.getProduct().getVendor().getId()
                        .equals(vendor.getId())) {

                    BigDecimal total = item.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));

                    vendorTotal = vendorTotal.add(total);

                    items.add(OrderItemResponse.builder()
                            .productId(item.getProduct().getId())
                            .productName(item.getProduct().getProductName())
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .build());
                }
            }

            if (!items.isEmpty()) {

                responses.add(
                        VendorOrderResponse.builder()
                                .orderId(order.getId())
                                .orderNumber(order.getOrderNumber())
                                .customerName(order.getCustomer().getFullName())
                                .customerEmail(order.getCustomer().getEmail())
                                .status(order.getStatus())
                                .totalAmount(vendorTotal)
                                .createdAt(order.getCreatedAt())
                                .items(items)
                                .build()
                );
            }
        }

        return responses;
    }

    @Override
    public VendorOrderResponse updateOrderStatus(
            Long orderId,
            String vendorEmail,
            UpdateOrderStatusRequest request) {

        User vendor = userRepository.findByEmail(vendorEmail)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        boolean ownsProduct = order.getOrderItems().stream()
                .anyMatch(item ->
                        item.getProduct()
                                .getVendor()
                                .getId()
                                .equals(vendor.getId()));

        if (!ownsProduct) {
            throw new RuntimeException("Unauthorized");
        }

        order.setStatus(request.getStatus());

        orderRepository.save(order);

        List<OrderItemResponse> items = new ArrayList<>();
        BigDecimal vendorTotal = BigDecimal.ZERO;

        for (OrderItem item : order.getOrderItems()) {

            if (item.getProduct().getVendor().getId()
                    .equals(vendor.getId())) {

                BigDecimal total = item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));

                vendorTotal = vendorTotal.add(total);

                items.add(OrderItemResponse.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getProductName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build());
            }
        }

        return VendorOrderResponse.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerName(order.getCustomer().getFullName())
                .customerEmail(order.getCustomer().getEmail())
                .status(order.getStatus())
                .totalAmount(vendorTotal)
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }
}