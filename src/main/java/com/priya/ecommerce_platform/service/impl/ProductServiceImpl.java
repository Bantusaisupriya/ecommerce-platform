package com.priya.ecommerce_platform.service.impl;

import com.priya.ecommerce_platform.dto.request.ProductRequest;
import com.priya.ecommerce_platform.dto.request.UpdateProductRequest;
import com.priya.ecommerce_platform.dto.response.ProductResponse;
import com.priya.ecommerce_platform.entity.Category;
import com.priya.ecommerce_platform.entity.Product;
import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.enums.ProductStatus;
import com.priya.ecommerce_platform.repository.CategoryRepository;
import com.priya.ecommerce_platform.repository.ProductRepository;
import com.priya.ecommerce_platform.repository.UserRepository;
import com.priya.ecommerce_platform.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public ProductResponse addProduct(
            String vendorEmail,
            ProductRequest request
    ) {

        User vendor = userRepository.findByEmail(vendorEmail)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        if (productRepository.existsBySku(request.getSku())) {
            throw new RuntimeException("SKU already exists");
        }

        validateImageUrl(request.getImageUrl());

        Product product = Product.builder()
                .productName(request.getProductName())
                .description(request.getDescription())
                .brand(request.getBrand())
                .sku(request.getSku())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .imageUrl(request.getImageUrl())
                .status(ProductStatus.ACTIVE)
                .vendor(vendor)
                .category(category)
                .build();

        product = productRepository.save(product);

        ProductResponse response = mapToResponse(product);
        response.setMessage("Product added successfully");

        return response;
    }

    @Override
    public List<ProductResponse> getVendorProducts(
            String vendorEmail
    ) {

        User vendor = userRepository.findByEmail(vendorEmail)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        return productRepository.findByVendor(vendor)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse updateProduct(
            Long productId,
            String vendorEmail,
            UpdateProductRequest request
    ) {

        User vendor = userRepository.findByEmail(vendorEmail)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        if (!product.getVendor().getId().equals(vendor.getId())) {
            throw new RuntimeException(
                    "You are not allowed to update this product");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        validateImageUrl(request.getImageUrl());

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        product = productRepository.save(product);

        ProductResponse response = mapToResponse(product);
        response.setMessage("Product updated successfully");

        return response;
    }

    @Override
    public void deleteProduct(
            Long productId,
            String vendorEmail
    ) {

        User vendor = userRepository.findByEmail(vendorEmail)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        if (!product.getVendor().getId().equals(vendor.getId())) {
            throw new RuntimeException(
                    "You are not allowed to delete this product");
        }

        productRepository.delete(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findByStatus(ProductStatus.ACTIVE)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(
            Long productId
    ) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        return mapToResponse(product);
    }

    @Override
    public List<ProductResponse> searchProducts(
            String keyword
    ) {

        return productRepository.findByProductNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getProductsByCategory(
            Long categoryId
    ) {

        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Validate Cloudinary URL.
     */
    private void validateImageUrl(String imageUrl) {

        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }

        if (!imageUrl.startsWith("https://res.cloudinary.com/")) {
            throw new RuntimeException(
                    "Invalid Cloudinary image URL");
        }
    }

    /**
     * Convert Product entity to ProductResponse.
     */
    private ProductResponse mapToResponse(Product product) {

        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .sku(product.getSku())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .imageUrl(product.getImageUrl())
                .status(product.getStatus())
                .categoryName(product.getCategory().getName())
                .vendorName(product.getVendor().getFullName())
                .build();
    }
}