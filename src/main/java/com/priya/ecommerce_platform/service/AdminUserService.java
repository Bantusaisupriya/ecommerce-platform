package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.response.UserResponse;

import java.util.List;

public interface AdminUserService {

    List<UserResponse> getAllUsers();

    List<UserResponse> getCustomers();

    List<UserResponse> getVendors();

    UserResponse blockUser(Long userId);

    UserResponse unblockUser(Long userId);

    void deleteUser(Long userId);
}