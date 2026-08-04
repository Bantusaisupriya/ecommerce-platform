package com.priya.ecommerce_platform.service.impl;

import com.priya.ecommerce_platform.dto.response.OrderItemResponse;
import com.priya.ecommerce_platform.dto.response.OrderResponse;
import com.priya.ecommerce_platform.entity.Order;
import com.priya.ecommerce_platform.entity.OrderItem;
import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.enums.OrderStatus;
import com.priya.ecommerce_platform.repository.OrderRepository;
import com.priya.ecommerce_platform.repository.UserRepository;
import com.priya.ecommerce_platform.service.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<OrderResponse> getCustomerOrders(String email) {

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        List<Order> orders = orderRepository.findByCustomer(customer);

        return orders.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse getCustomerOrderById(
            Long orderId,
            String email
    ) {

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        if (!order.getCustomer().getId().equals(customer.getId())) {
            throw new RuntimeException(
                    "You are not authorized to view this order"
            );
        }

        return mapToResponse(order);
    }

    @Override
    public OrderResponse cancelOrder(
            Long orderId,
            String email
    ) {

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        if (!order.getCustomer().getId().equals(customer.getId())) {
            throw new RuntimeException(
                    "You are not authorized to cancel this order"
            );
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException(
                    "Only pending orders can be cancelled"
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        return mapToResponse(order);
    }

    private OrderResponse mapToResponse(Order order) {

        List<OrderItemResponse> items = order.getOrderItems()
                .stream()
                .map(this::mapItemToResponse)
                .toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }

    private OrderItemResponse mapItemToResponse(OrderItem item) {

        return OrderItemResponse.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getProductName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }
}