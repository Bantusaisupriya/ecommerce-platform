package com.priya.ecommerce_platform.service.impl;


import com.priya.ecommerce_platform.dto.request.CreatePaymentRequest;
import com.priya.ecommerce_platform.dto.request.VerifyPaymentRequest;
import com.priya.ecommerce_platform.dto.response.PaymentResponse;

import com.priya.ecommerce_platform.entity.Order;
import com.priya.ecommerce_platform.entity.Payment;

import com.priya.ecommerce_platform.enums.OrderStatus;
import com.priya.ecommerce_platform.enums.PaymentMethod;
import com.priya.ecommerce_platform.enums.PaymentStatus;

import com.priya.ecommerce_platform.repository.OrderRepository;
import com.priya.ecommerce_platform.repository.PaymentRepository;

import com.priya.ecommerce_platform.service.EmailService;
import com.priya.ecommerce_platform.service.PaymentService;

import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

import lombok.RequiredArgsConstructor;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;



@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {



    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    private final RazorpayClient razorpayClient;

    private final EmailService emailService;




    @Value("${razorpay.key.id}")
    private String razorpayKey;



    @Value("${razorpay.key.secret}")
    private String razorpaySecret;





    @Override
    public PaymentResponse createPaymentOrder(
            CreatePaymentRequest request
    ) throws Exception {



        Order order =
                orderRepository.findById(request.getOrderId())

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"
                                )
                        );




        if(paymentRepository
                .findByOrderId(order.getId())
                .isPresent()) {


            throw new RuntimeException(
                    "Payment already exists for this order"
            );

        }






        JSONObject options =
                new JSONObject();



        options.put(
                "amount",
                convertToPaise(
                        order.getTotalAmount()
                )
        );


        options.put(
                "currency",
                "INR"
        );


        options.put(
                "receipt",
                order.getOrderNumber()
        );





        com.razorpay.Order razorpayOrder =
                razorpayClient.orders.create(options);






        Payment payment =
                Payment.builder()

                        .order(order)

                        .amount(
                                order.getTotalAmount()
                        )

                        .paymentMethod(
                                PaymentMethod.RAZORPAY
                        )

                        .paymentStatus(
                                PaymentStatus.PENDING
                        )

                        .razorpayOrderId(
                                razorpayOrder.get("id")
                        )

                        .build();




        payment =
                paymentRepository.save(payment);





        return buildResponse(
                payment,
                "Razorpay order created successfully"
        );

    }









    @Override
    public PaymentResponse verifyPayment(
            VerifyPaymentRequest request
    ) throws Exception {



        Payment payment =
                paymentRepository
                        .findByRazorpayOrderId(
                                request.getRazorpayOrderId()
                        )

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found"
                                )
                        );





        JSONObject options =
                new JSONObject();



        options.put(
                "razorpay_order_id",
                request.getRazorpayOrderId()
        );


        options.put(
                "razorpay_payment_id",
                request.getRazorpayPaymentId()
        );


        options.put(
                "razorpay_signature",
                request.getRazorpaySignature()
        );







        boolean verified =
                Utils.verifyPaymentSignature(
                        options,
                        razorpaySecret
                );





        if(!verified){

            throw new RuntimeException(
                    "Invalid payment signature"
            );

        }







        payment.setPaymentStatus(
                PaymentStatus.SUCCESS
        );


        payment.setRazorpayPaymentId(
                request.getRazorpayPaymentId()
        );


        payment.setRazorpaySignature(
                request.getRazorpaySignature()
        );



        payment =
                paymentRepository.save(payment);







        Order order =
                payment.getOrder();



        order.setStatus(
                OrderStatus.CONFIRMED
        );



        orderRepository.save(order);






        // Send payment success email

        emailService.sendPaymentSuccessEmail(
                order.getCustomer()
                        .getEmail(),

                order.getOrderNumber()
        );







        return buildResponse(
                payment,
                "Payment verified successfully"
        );

    }









    @Override
    public PaymentResponse getPayment(
            Long orderId
    ) throws Exception {



        Payment payment =
                paymentRepository.findByOrderId(orderId)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found"
                                )
                        );



        return buildResponse(
                payment,
                "Payment fetched successfully"
        );

    }









    private Integer convertToPaise(
            BigDecimal amount
    ){


        if(amount == null){

            return 0;

        }


        return amount
                .multiply(
                        BigDecimal.valueOf(100)
                )
                .intValue();

    }









    private PaymentResponse buildResponse(
            Payment payment,
            String message
    ){



        Order order =
                payment.getOrder();




        return PaymentResponse.builder()

                .paymentId(
                        payment.getId()
                )

                .orderId(
                        order.getId()
                )

                .orderNumber(
                        order.getOrderNumber()
                )

                .amount(
                        payment.getAmount()
                )

                .paymentMethod(
                        payment.getPaymentMethod()
                )

                .paymentStatus(
                        payment.getPaymentStatus()
                )

                .razorpayOrderId(
                        payment.getRazorpayOrderId()
                )

                .razorpayAmount(
                        convertToPaise(
                                payment.getAmount()
                        )
                )

                .currency(
                        "INR"
                )

                .key(
                        razorpayKey
                )

                .message(
                        message
                )

                .build();

    }

}