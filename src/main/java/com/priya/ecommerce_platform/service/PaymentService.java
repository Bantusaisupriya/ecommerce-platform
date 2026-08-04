package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.request.CreatePaymentRequest;
import com.priya.ecommerce_platform.dto.request.VerifyPaymentRequest;
import com.priya.ecommerce_platform.dto.response.PaymentResponse;

public interface PaymentService {

    /**
     * Create Razorpay payment order
     */
    PaymentResponse createPaymentOrder(
            CreatePaymentRequest request
    ) throws Exception;

    /**
     * Verify Razorpay payment
     */
    PaymentResponse verifyPayment(
            VerifyPaymentRequest request
    ) throws Exception;

    /**
     * Get payment details by Order ID
     */
    PaymentResponse getPayment(
            Long orderId
    ) throws Exception;

}