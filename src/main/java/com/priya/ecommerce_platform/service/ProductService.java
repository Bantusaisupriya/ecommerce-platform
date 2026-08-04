package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.request.ProductRequest;
import com.priya.ecommerce_platform.dto.request.UpdateProductRequest;
import com.priya.ecommerce_platform.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    // Vendor APIs

    ProductResponse addProduct(
            String vendorEmail,
            ProductRequest request
    );

    List<ProductResponse> getVendorProducts(
            String vendorEmail
    );

    ProductResponse updateProduct(
            Long productId,
            String vendorEmail,
            UpdateProductRequest request
    );

    void deleteProduct(
            Long productId,
            String vendorEmail
    );

    // Customer APIs

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long productId);

    List<ProductResponse> searchProducts(String keyword);

    List<ProductResponse> getProductsByCategory(Long categoryId);

}