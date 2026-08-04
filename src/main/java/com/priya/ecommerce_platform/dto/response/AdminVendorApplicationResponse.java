package com.priya.ecommerce_platform.dto.response;

import com.priya.ecommerce_platform.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminVendorApplicationResponse {

    private Long applicationId;

    private String businessName;

    private String ownerName;

    private String email;

    private String phone;

    private String gstNumber;

    private String address;

    private String description;

    private ApplicationStatus status;

    private String userName;

    private LocalDateTime createdAt;
}