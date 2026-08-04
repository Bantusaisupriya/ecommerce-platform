package com.priya.ecommerce_platform.controller;


import com.priya.ecommerce_platform.service.EmailService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/test/email")
@RequiredArgsConstructor
public class EmailTestController {



    private final EmailService emailService;



    @PostMapping
    public String sendTestEmail(
            @RequestParam String email
    ){

        emailService.sendEmail(
                email,
                "Test Email",
                "Email service working successfully"
        );


        return "Email sent successfully";

    }

}