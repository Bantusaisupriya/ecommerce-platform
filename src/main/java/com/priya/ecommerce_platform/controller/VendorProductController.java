package com.priya.ecommerce_platform.controller;

import com.priya.ecommerce_platform.dto.request.ProductRequest;
import com.priya.ecommerce_platform.dto.request.UpdateProductRequest;
import com.priya.ecommerce_platform.dto.response.ApiResponse;
import com.priya.ecommerce_platform.dto.response.ProductResponse;
import com.priya.ecommerce_platform.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor/products")
@RequiredArgsConstructor
public class VendorProductController {

    private final ProductService productService;

    // Add Product
    @PostMapping
    public ApiResponse<ProductResponse> addProduct(
            Authentication authentication,
            @Valid @RequestBody ProductRequest request) {

        ProductResponse response = productService.addProduct(
                authentication.getName(),
                request
        );

        return ApiResponse.success(
                "Product added successfully",
                response
        );
    }

    // View My Products
    @GetMapping
    public ApiResponse<List<ProductResponse>> getMyProducts(
            Authentication authentication) {

        List<ProductResponse> products =
                productService.getVendorProducts(authentication.getName());

        return ApiResponse.success(
                "Products fetched successfully",
                products
        );
    }

    // Update Product
    @PutMapping("/{productId}")
    public ApiResponse<ProductResponse> updateProduct(
            @PathVariable Long productId,
            Authentication authentication,
            @Valid @RequestBody UpdateProductRequest request) {

        ProductResponse response = productService.updateProduct(
                productId,
                authentication.getName(),
                request
        );

        return ApiResponse.success(
                "Product updated successfully",
                response
        );
    }
    @DeleteMapping("/{productId}")
    public ApiResponse<String> deleteProduct(
            @PathVariable Long productId,
            Authentication authentication) {

        productService.deleteProduct(
                productId,
                authentication.getName()
        );

        return ApiResponse.success(
                "Product deleted successfully",
                "Product deleted successfully"
        );
    }
}