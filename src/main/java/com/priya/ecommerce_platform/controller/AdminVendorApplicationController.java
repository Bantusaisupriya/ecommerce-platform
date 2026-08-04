package com.priya.ecommerce_platform.controller;

import com.priya.ecommerce_platform.dto.response.AdminVendorApplicationResponse;
import com.priya.ecommerce_platform.dto.response.ApiResponse;
import com.priya.ecommerce_platform.service.AdminVendorApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/vendor-applications")
@RequiredArgsConstructor
public class AdminVendorApplicationController {

    private final AdminVendorApplicationService adminVendorApplicationService;

    @GetMapping
    public ApiResponse<List<AdminVendorApplicationResponse>> getAllApplications() {

        return ApiResponse.success(
                "Applications fetched successfully",
                adminVendorApplicationService.getAllApplications()
        );
    }

    @GetMapping("/{applicationId}")
    public ApiResponse<AdminVendorApplicationResponse> getApplicationById(
            @PathVariable Long applicationId) {

        return ApiResponse.success(
                "Application fetched successfully",
                adminVendorApplicationService.getApplicationById(applicationId)
        );
    }

    @PutMapping("/{applicationId}/approve")
    public ApiResponse<AdminVendorApplicationResponse> approveApplication(
            @PathVariable Long applicationId) {

        return ApiResponse.success(
                "Vendor approved successfully",
                adminVendorApplicationService.approveApplication(applicationId)
        );
    }

    @PutMapping("/{applicationId}/reject")
    public ApiResponse<AdminVendorApplicationResponse> rejectApplication(
            @PathVariable Long applicationId) {

        return ApiResponse.success(
                "Vendor rejected successfully",
                adminVendorApplicationService.rejectApplication(applicationId)
        );
    }
}