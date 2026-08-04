package com.priya.ecommerce_platform.controller;

import com.priya.ecommerce_platform.dto.request.VendorApplicationRequest;
import com.priya.ecommerce_platform.dto.response.VendorApplicationResponse;
import com.priya.ecommerce_platform.service.VendorApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor")
@RequiredArgsConstructor
public class VendorController {

    private final VendorApplicationService vendorApplicationService;

    @PostMapping("/apply")
    public VendorApplicationResponse applyForVendor(
            Authentication authentication,
            @Valid @RequestBody VendorApplicationRequest request) {

        String email = authentication.getName();

        return vendorApplicationService.applyForVendor(email, request);
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Welcome Vendor";
    }
}