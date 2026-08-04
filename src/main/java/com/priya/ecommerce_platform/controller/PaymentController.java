package com.priya.ecommerce_platform.controller;

import com.priya.ecommerce_platform.dto.request.CreatePaymentRequest;
import com.priya.ecommerce_platform.dto.request.VerifyPaymentRequest;
import com.priya.ecommerce_platform.dto.response.ApiResponse;
import com.priya.ecommerce_platform.dto.response.PaymentResponse;
import com.priya.ecommerce_platform.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Create Razorpay Order
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @Valid @RequestBody CreatePaymentRequest request)
            throws Exception {

        PaymentResponse response =
                paymentService.createPaymentOrder(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment order created successfully",
                        response
                )
        );
    }

    /**
     * Verify Razorpay Payment
     */
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<PaymentResponse>> verifyPayment(
            @Valid @RequestBody VerifyPaymentRequest request)
            throws Exception {

        PaymentResponse response =
                paymentService.verifyPayment(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment verified successfully",
                        response
                )
        );
    }

    /**
     * Get Payment by Order ID
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(
            @PathVariable Long orderId)
            throws Exception {

        PaymentResponse response =
                paymentService.getPayment(orderId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment fetched successfully",
                        response
                )
        );
    }
}