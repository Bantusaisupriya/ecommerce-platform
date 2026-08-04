package com.priya.ecommerce_platform.service.impl;

import com.priya.ecommerce_platform.dto.response.AdminVendorApplicationResponse;
import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.entity.VendorApplication;
import com.priya.ecommerce_platform.enums.ApplicationStatus;
import com.priya.ecommerce_platform.enums.Role;
import com.priya.ecommerce_platform.repository.UserRepository;
import com.priya.ecommerce_platform.repository.VendorApplicationRepository;
import com.priya.ecommerce_platform.service.AdminVendorApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminVendorApplicationServiceImpl
        implements AdminVendorApplicationService {

    private final VendorApplicationRepository vendorApplicationRepository;
    private final UserRepository userRepository;

    @Override
    public List<AdminVendorApplicationResponse> getAllApplications() {

        return vendorApplicationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AdminVendorApplicationResponse getApplicationById(Long applicationId) {

        VendorApplication application =
                vendorApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException("Application not found"));

        return mapToResponse(application);
    }

    @Override
    public AdminVendorApplicationResponse approveApplication(Long applicationId) {

        VendorApplication application =
                vendorApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException("Application not found"));

        application.setStatus(ApplicationStatus.APPROVED);

        User user = application.getUser();
        user.setRole(Role.VENDOR);

        userRepository.save(user);
        vendorApplicationRepository.save(application);

        return mapToResponse(application);
    }

    @Override
    public AdminVendorApplicationResponse rejectApplication(Long applicationId) {

        VendorApplication application =
                vendorApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException("Application not found"));

        application.setStatus(ApplicationStatus.REJECTED);

        vendorApplicationRepository.save(application);

        return mapToResponse(application);
    }

    private AdminVendorApplicationResponse mapToResponse(
            VendorApplication application) {

        return AdminVendorApplicationResponse.builder()
                .applicationId(application.getId())
                .businessName(application.getBusinessName())
                .ownerName(application.getOwnerName())
                .email(application.getEmail())
                .phone(application.getPhone())
                .gstNumber(application.getGstNumber())
                .address(application.getAddress())
                .description(application.getDescription())
                .status(application.getStatus())
                .userName(application.getUser().getFullName())
                .createdAt(application.getCreatedAt())
                .build();
    }
}