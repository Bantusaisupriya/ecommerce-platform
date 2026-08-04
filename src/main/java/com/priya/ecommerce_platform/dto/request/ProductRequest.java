package com.priya.ecommerce_platform.dto.request;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;


import java.math.BigDecimal;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {


    @NotBlank(message = "Product name is required")
    private String productName;



    private String description;



    @NotBlank(message = "Brand is required")
    private String brand;



    @NotBlank(message = "SKU is required")
    private String sku;



    @NotNull(message = "Price is required")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "Price must be greater than zero"
    )
    private BigDecimal price;



    @NotNull(message = "Stock quantity is required")
    private Integer stockQuantity;



    /*
     * Cloudinary image URL
     */
    private String imageUrl;



    @NotNull(message = "Category ID is required")
    private Long categoryId;

}