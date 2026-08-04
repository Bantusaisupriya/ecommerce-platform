package com.priya.ecommerce_platform.controller;


import com.priya.ecommerce_platform.service.InvoiceService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/customer/orders")
@RequiredArgsConstructor
public class InvoiceController {



    private final InvoiceService invoiceService;



    /**
     * Download Order Invoice PDF
     *
     * GET
     * /api/customer/orders/{orderId}/invoice
     */
    @GetMapping("/{orderId}/invoice")
    public ResponseEntity<byte[]> downloadInvoice(
            @PathVariable Long orderId,
            Authentication authentication
    ) throws Exception {



        byte[] pdf =
                invoiceService.generateInvoice(
                        orderId,
                        authentication.getName()
                );



        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=invoice-"
                                + orderId
                                + ".pdf"
                )

                .contentType(
                        MediaType.APPLICATION_PDF
                )

                .body(pdf);

    }

}