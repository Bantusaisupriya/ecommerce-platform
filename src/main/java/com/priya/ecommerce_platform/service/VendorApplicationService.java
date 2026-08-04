package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.request.VendorApplicationRequest;
import com.priya.ecommerce_platform.dto.response.VendorApplicationResponse;

import java.util.List;

public interface VendorApplicationService {

    VendorApplicationResponse applyForVendor(
            String email,
            VendorApplicationRequest request
    );

    List<VendorApplicationResponse> getAllApplications();

    VendorApplicationResponse approveVendor(Long applicationId);

    VendorApplicationResponse rejectVendor(Long applicationId);
}