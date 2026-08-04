package com.priya.ecommerce_platform.service.impl;


import com.priya.ecommerce_platform.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {



    private final JavaMailSender mailSender;





    @Override
    public void sendEmail(
            String to,
            String subject,
            String body
    ) {


        SimpleMailMessage message =
                new SimpleMailMessage();


        message.setTo(to);

        message.setSubject(subject);

        message.setText(body);


        mailSender.send(message);

    }








    @Override
    public void sendOrderConfirmationEmail(
            String email,
            String orderNumber
    ) {


        String subject =
                "Order Confirmation - "
                        + orderNumber;



        String body =
                """
                Hello,

                Your order has been placed successfully.

                Order Number:
                %s


                Thank you for shopping with us.

                Regards,
                E-Commerce Team
                """
                        .formatted(orderNumber);



        sendEmail(
                email,
                subject,
                body
        );

    }









    @Override
    public void sendPaymentSuccessEmail(
            String email,
            String orderNumber
    ) {


        String subject =
                "Payment Successful - "
                        + orderNumber;



        String body =
                """
                Hello,

                Your payment has been successfully received.

                Order Number:
                %s


                Your order is being processed.

                Thank you.

                E-Commerce Team
                """
                        .formatted(orderNumber);



        sendEmail(
                email,
                subject,
                body
        );

    }










    @Override
    public void sendVendorApprovalEmail(
            String email,
            String businessName
    ) {


        String subject =
                "Vendor Application Approved";



        String body =
                """
                Hello,

                Congratulations!

                Your vendor application for:

                %s


                has been approved.

                You can now start adding products.

                Regards,
                Admin Team
                """
                        .formatted(businessName);



        sendEmail(
                email,
                subject,
                body
        );

    }


}