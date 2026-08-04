package com.priya.ecommerce_platform.controller;

import com.priya.ecommerce_platform.dto.response.AdminDashboardResponse;
import com.priya.ecommerce_platform.dto.response.ApiResponse;
import com.priya.ecommerce_platform.dto.response.UserResponse;
import com.priya.ecommerce_platform.service.AdminDashboardService;
import com.priya.ecommerce_platform.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class SuperAdminController {

    private final AdminDashboardService adminDashboardService;

    private final AdminUserService adminUserService;

    // ===========================
    // Dashboard
    // ===========================

    @GetMapping("/dashboard")
    public ApiResponse<AdminDashboardResponse> getDashboard() {

        return ApiResponse.success(
                "Dashboard fetched successfully",
                adminDashboardService.getDashboard()
        );
    }

    // ===========================
    // User Management
    // ===========================

    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getAllUsers() {

        return ApiResponse.success(
                "Users fetched successfully",
                adminUserService.getAllUsers()
        );
    }

    @GetMapping("/customers")
    public ApiResponse<List<UserResponse>> getCustomers() {

        return ApiResponse.success(
                "Customers fetched successfully",
                adminUserService.getCustomers()
        );
    }

    @GetMapping("/vendors")
    public ApiResponse<List<UserResponse>> getVendors() {

        return ApiResponse.success(
                "Vendors fetched successfully",
                adminUserService.getVendors()
        );
    }

    @PutMapping("/users/{id}/block")
    public ApiResponse<UserResponse> blockUser(
            @PathVariable Long id
    ) {

        return ApiResponse.success(
                "User blocked successfully",
                adminUserService.blockUser(id)
        );
    }

    @PutMapping("/users/{id}/unblock")
    public ApiResponse<UserResponse> unblockUser(
            @PathVariable Long id
    ) {

        return ApiResponse.success(
                "User unblocked successfully",
                adminUserService.unblockUser(id)
        );
    }

    @DeleteMapping("/users/{id}")
    public ApiResponse<String> deleteUser(
            @PathVariable Long id
    ) {

        adminUserService.deleteUser(id);

        return ApiResponse.success(
                "User deleted successfully",
                null
        );
    }

}