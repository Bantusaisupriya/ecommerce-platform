package com.priya.ecommerce_platform.service;


public interface EmailService {


    void sendEmail(
            String to,
            String subject,
            String body
    );


    void sendOrderConfirmationEmail(
            String email,
            String orderNumber
    );


    void sendPaymentSuccessEmail(
            String email,
            String orderNumber
    );


    void sendVendorApprovalEmail(
            String email,
            String businessName
    );

}