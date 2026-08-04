package com.priya.ecommerce_platform.controller;

import com.priya.ecommerce_platform.dto.response.ApiResponse;
import com.priya.ecommerce_platform.dto.response.ProductResponse;
import com.priya.ecommerce_platform.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/products")
@RequiredArgsConstructor
public class CustomerProductController {

    private final ProductService productService;

    // Get All Products
    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts() {

        return ApiResponse.success(
                "Products fetched successfully",
                productService.getAllProducts()
        );
    }

    // Get Product By ID
    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProductById(
            @PathVariable Long productId) {

        return ApiResponse.success(
                "Product fetched successfully",
                productService.getProductById(productId)
        );
    }

    // Search Products
    @GetMapping("/search")
    public ApiResponse<List<ProductResponse>> searchProducts(
            @RequestParam String keyword) {

        return ApiResponse.success(
                "Products found",
                productService.searchProducts(keyword)
        );
    }

    // Filter by Category
    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<ProductResponse>> getProductsByCategory(
            @PathVariable Long categoryId) {

        return ApiResponse.success(
                "Products fetched successfully",
                productService.getProductsByCategory(categoryId)
        );
    }
}