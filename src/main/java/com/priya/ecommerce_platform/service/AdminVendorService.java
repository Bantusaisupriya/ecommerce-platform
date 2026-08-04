package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.response.VendorApplicationResponse;

import java.util.List;

public interface AdminVendorService {

    List<VendorApplicationResponse> getAllApplications();

    VendorApplicationResponse approveApplication(Long applicationId);

    VendorApplicationResponse rejectApplication(Long applicationId);
}