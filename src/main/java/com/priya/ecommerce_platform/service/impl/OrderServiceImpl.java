package com.priya.ecommerce_platform.service.impl;

import com.priya.ecommerce_platform.dto.request.PlaceOrderRequest;
import com.priya.ecommerce_platform.dto.response.OrderItemResponse;
import com.priya.ecommerce_platform.dto.response.OrderResponse;
import com.priya.ecommerce_platform.entity.*;
import com.priya.ecommerce_platform.enums.OrderStatus;
import com.priya.ecommerce_platform.repository.*;
import com.priya.ecommerce_platform.service.EmailService;
import com.priya.ecommerce_platform.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final EmailService emailService;



    @Override
    public OrderResponse placeOrder(
            String customerEmail,
            PlaceOrderRequest request
    ) {


        User customer =
                userRepository.findByEmail(customerEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer not found"
                                )
                        );



        Cart cart =
                cartRepository.findById(request.getCartId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cart not found"
                                )
                        );



        if (!cart.getUser()
                .getId()
                .equals(customer.getId())) {

            throw new RuntimeException(
                    "Unauthorized"
            );
        }




        List<CartItem> cartItems =
                cartItemRepository.findByCart(cart);



        if (cartItems.isEmpty()) {

            throw new RuntimeException(
                    "Cart is empty"
            );

        }




        BigDecimal totalAmount =
                BigDecimal.ZERO;


        List<OrderItem> orderItems =
                new ArrayList<>();





        Order order =
                Order.builder()

                        .orderNumber(
                                "ORD-"
                                        + UUID.randomUUID()
                                        .toString()
                                        .substring(0,8)
                                        .toUpperCase()
                        )

                        .customer(customer)

                        .status(
                                OrderStatus.PENDING_PAYMENT
                        )

                        .totalAmount(
                                BigDecimal.ZERO
                        )

                        .build();




        order =
                orderRepository.save(order);





        for (CartItem cartItem : cartItems) {


            Product product =
                    cartItem.getProduct();




            if(product.getStockQuantity()
                    < cartItem.getQuantity()) {


                throw new RuntimeException(
                        "Insufficient stock for "
                                + product.getProductName()
                );

            }





            product.setStockQuantity(
                    product.getStockQuantity()
                            -
                            cartItem.getQuantity()
            );


            productRepository.save(product);





            BigDecimal itemTotal =
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            cartItem.getQuantity()
                                    )
                            );



            totalAmount =
                    totalAmount.add(itemTotal);






            OrderItem orderItem =
                    OrderItem.builder()

                            .order(order)

                            .product(product)

                            .quantity(
                                    cartItem.getQuantity()
                            )

                            .price(
                                    product.getPrice()
                            )

                            .build();



            orderItemRepository.save(orderItem);


            orderItems.add(orderItem);

        }







        order.setOrderItems(orderItems);


        order.setTotalAmount(totalAmount);



        order =
                orderRepository.save(order);





        // Email notification
        emailService.sendOrderConfirmationEmail(
                customer.getEmail(),
                order.getOrderNumber()
        );





        // Clear cart
        cartItemRepository.deleteAll(cartItems);





        return mapToResponse(order);

    }







    @Override
    public List<OrderResponse> getMyOrders(
            String customerEmail
    ) {


        User customer =
                userRepository.findByEmail(customerEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer not found"
                                )
                        );



        return orderRepository.findByCustomer(customer)

                .stream()

                .map(this::mapToResponse)

                .toList();

    }








    @Override
    public OrderResponse getOrderById(
            Long orderId,
            String customerEmail
    ) {


        User customer =
                userRepository.findByEmail(customerEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer not found"
                                )
                        );



        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"
                                )
                        );



        if(!order.getCustomer()
                .getId()
                .equals(customer.getId())) {


            throw new RuntimeException(
                    "Unauthorized"
            );

        }



        return mapToResponse(order);

    }








    @Override
    public void cancelOrder(
            Long orderId,
            String customerEmail
    ) {


        User customer =
                userRepository.findByEmail(customerEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer not found"
                                )
                        );



        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"
                                )
                        );



        if(!order.getCustomer()
                .getId()
                .equals(customer.getId())) {


            throw new RuntimeException(
                    "Unauthorized"
            );

        }



        order.setStatus(
                OrderStatus.CANCELLED
        );


        orderRepository.save(order);

    }








    private OrderResponse mapToResponse(
            Order order
    ) {



        List<OrderItemResponse> items =
                order.getOrderItems() == null

                        ? new ArrayList<>()

                        :

                        order.getOrderItems()
                                .stream()

                                .map(item ->
                                        OrderItemResponse.builder()

                                                .productId(
                                                        item.getProduct()
                                                                .getId()
                                                )

                                                .productName(
                                                        item.getProduct()
                                                                .getProductName()
                                                )

                                                .quantity(
                                                        item.getQuantity()
                                                )

                                                .price(
                                                        item.getPrice()
                                                )

                                                .build()
                                )

                                .toList();





        return OrderResponse.builder()

                .orderId(
                        order.getId()
                )

                .orderNumber(
                        order.getOrderNumber()
                )

                .totalAmount(
                        order.getTotalAmount()
                )

                .status(
                        order.getStatus()
                )

                .createdAt(
                        order.getCreatedAt()
                )

                .items(items)

                .build();

    }

}