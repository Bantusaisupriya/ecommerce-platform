package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.request.CategoryRequest;
import com.priya.ecommerce_platform.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);
}