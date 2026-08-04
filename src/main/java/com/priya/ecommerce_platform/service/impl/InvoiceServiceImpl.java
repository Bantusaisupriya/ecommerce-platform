package com.priya.ecommerce_platform.service.impl;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;


import com.priya.ecommerce_platform.entity.Order;
import com.priya.ecommerce_platform.entity.OrderItem;
import com.priya.ecommerce_platform.entity.User;

import com.priya.ecommerce_platform.repository.OrderRepository;
import com.priya.ecommerce_platform.repository.UserRepository;

import com.priya.ecommerce_platform.service.InvoiceService;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;



@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {



    private final OrderRepository orderRepository;

    private final UserRepository userRepository;





    @Override
    public byte[] generateInvoice(
            Long orderId,
            String customerEmail
    ) throws Exception {



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
                .equals(customer.getId())){


            throw new RuntimeException(
                    "Unauthorized access"
            );

        }





        ByteArrayOutputStream output =
                new ByteArrayOutputStream();



        PdfWriter writer =
                new PdfWriter(output);



        PdfDocument pdf =
                new PdfDocument(writer);



        Document document =
                new Document(pdf);





        document.add(
                new Paragraph(
                        "E-Commerce Platform Invoice"
                )
        );



        document.add(
                new Paragraph(
                        "Order Number : "
                                + order.getOrderNumber()
                )
        );



        document.add(
                new Paragraph(
                        "Customer : "
                                + customer.getFullName()
                )
        );



        document.add(
                new Paragraph(
                        "Email : "
                                + customer.getEmail()
                )
        );



        document.add(
                new Paragraph(
                        "Status : "
                                + order.getStatus()
                )
        );





        Table table =
                new Table(4);



        table.addCell(
                new Cell()
                        .add(
                                new Paragraph("Product")
                        )
        );


        table.addCell(
                new Cell()
                        .add(
                                new Paragraph("Quantity")
                        )
        );


        table.addCell(
                new Cell()
                        .add(
                                new Paragraph("Price")
                        )
        );


        table.addCell(
                new Cell()
                        .add(
                                new Paragraph("Total")
                        )
        );





        for(OrderItem item :
                order.getOrderItems()){



            table.addCell(
                    item.getProduct()
                            .getProductName()
            );


            table.addCell(
                    String.valueOf(
                            item.getQuantity()
                    )
            );


            table.addCell(
                    item.getPrice()
                            .toString()
            );


            table.addCell(
                    item.getPrice()
                            .multiply(
                                    java.math.BigDecimal
                                            .valueOf(
                                                    item.getQuantity()
                                            )
                            )
                            .toString()
            );


        }



        document.add(table);




        document.add(
                new Paragraph(
                        "Total Amount : ₹ "
                                + order.getTotalAmount()
                )
        );



        document.close();



        return output.toByteArray();

    }


}