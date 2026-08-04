package com.priya.ecommerce_platform.dto.response;

import com.priya.ecommerce_platform.enums.ProductStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long productId;
    private String productName;
    private String description;
    private String brand;
    private String sku;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private ProductStatus status;
    private String categoryName;
    private String vendorName;
    private String message;
}