package com.priya.ecommerce_platform.service.impl;

import com.priya.ecommerce_platform.dto.response.VendorApplicationResponse;
import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.entity.VendorApplication;
import com.priya.ecommerce_platform.enums.ApplicationStatus;
import com.priya.ecommerce_platform.enums.Role;
import com.priya.ecommerce_platform.repository.UserRepository;
import com.priya.ecommerce_platform.repository.VendorApplicationRepository;
import com.priya.ecommerce_platform.service.AdminVendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminVendorServiceImpl implements AdminVendorService {

    private final VendorApplicationRepository vendorApplicationRepository;
    private final UserRepository userRepository;

    @Override
    public List<VendorApplicationResponse> getAllApplications() {

        return vendorApplicationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public VendorApplicationResponse approveApplication(Long applicationId) {

        VendorApplication application = vendorApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(ApplicationStatus.APPROVED);

        User user = application.getUser();
        user.setRole(Role.VENDOR);

        userRepository.save(user);
        vendorApplicationRepository.save(application);

        return mapToResponse(application);
    }

    @Override
    public VendorApplicationResponse rejectApplication(Long applicationId) {

        VendorApplication application = vendorApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(ApplicationStatus.REJECTED);

        vendorApplicationRepository.save(application);

        return mapToResponse(application);
    }

    private VendorApplicationResponse mapToResponse(VendorApplication application) {

        return VendorApplicationResponse.builder()
                .businessName(application.getBusinessName())
                .ownerName(application.getOwnerName())
                .gstNumber(application.getGstNumber())
                .phone(application.getPhone())
                .email(application.getEmail())
                .address(application.getAddress())
                .description(application.getDescription())
                .status(application.getStatus())
                .build();
    }
}