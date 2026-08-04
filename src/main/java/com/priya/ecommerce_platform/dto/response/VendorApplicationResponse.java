package com.priya.ecommerce_platform.dto.response;

import com.priya.ecommerce_platform.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendorApplicationResponse {

    private Long applicationId;

    private String businessName;

    private String ownerName;

    private String gstNumber;

    private String phone;

    private String email;

    private String address;

    private String description;

    private ApplicationStatus status;

    private String message;
}