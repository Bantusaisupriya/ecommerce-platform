package com.priya.ecommerce_platform.service;


public interface InvoiceService {


    byte[] generateInvoice(
            Long orderId,
            String customerEmail
    ) throws Exception;


}