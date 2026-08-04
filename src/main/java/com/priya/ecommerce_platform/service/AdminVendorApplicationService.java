package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.response.AdminVendorApplicationResponse;

import java.util.List;

public interface AdminVendorApplicationService {

    List<AdminVendorApplicationResponse> getAllApplications();

    AdminVendorApplicationResponse getApplicationById(Long applicationId);

    AdminVendorApplicationResponse approveApplication(Long applicationId);

    AdminVendorApplicationResponse rejectApplication(Long applicationId);
}   