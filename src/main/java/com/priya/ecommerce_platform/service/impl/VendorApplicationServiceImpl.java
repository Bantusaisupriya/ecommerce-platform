package com.priya.ecommerce_platform.service.impl;


import com.priya.ecommerce_platform.dto.request.VendorApplicationRequest;
import com.priya.ecommerce_platform.dto.response.VendorApplicationResponse;

import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.entity.VendorApplication;

import com.priya.ecommerce_platform.enums.ApplicationStatus;
import com.priya.ecommerce_platform.enums.Role;

import com.priya.ecommerce_platform.repository.UserRepository;
import com.priya.ecommerce_platform.repository.VendorApplicationRepository;

import com.priya.ecommerce_platform.service.EmailService;
import com.priya.ecommerce_platform.service.VendorApplicationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class VendorApplicationServiceImpl
        implements VendorApplicationService {




    private final VendorApplicationRepository vendorApplicationRepository;

    private final UserRepository userRepository;

    private final EmailService emailService;





    @Override
    public VendorApplicationResponse applyForVendor(
            String email,
            VendorApplicationRequest request
    ) {



        User user =
                userRepository.findByEmail(email)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );





        if(vendorApplicationRepository
                .existsByUser(user)) {


            throw new RuntimeException(
                    "You have already submitted a vendor application."
            );

        }





        VendorApplication application =
                VendorApplication.builder()

                        .businessName(
                                request.getBusinessName()
                        )

                        .ownerName(
                                request.getOwnerName()
                        )

                        .gstNumber(
                                request.getGstNumber()
                        )

                        .phone(
                                request.getPhone()
                        )

                        .email(
                                request.getEmail()
                        )

                        .address(
                                request.getAddress()
                        )

                        .description(
                                request.getDescription()
                        )

                        .status(
                                ApplicationStatus.PENDING
                        )

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .updatedAt(
                                LocalDateTime.now()
                        )

                        .user(user)

                        .build();





        vendorApplicationRepository.save(application);





        return mapResponse(
                application,
                "Vendor application submitted successfully."
        );

    }









    @Override
    public List<VendorApplicationResponse> getAllApplications() {



        return vendorApplicationRepository.findAll()

                .stream()

                .map(application ->
                        mapResponse(
                                application,
                                "Application Retrieved Successfully"
                        )
                )

                .collect(Collectors.toList());

    }









    @Override
    public VendorApplicationResponse approveVendor(
            Long applicationId
    ) {



        VendorApplication application =
                vendorApplicationRepository
                        .findById(applicationId)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"
                                )
                        );





        application.setStatus(
                ApplicationStatus.APPROVED
        );


        application.setUpdatedAt(
                LocalDateTime.now()
        );





        User user =
                application.getUser();



        user.setRole(
                Role.VENDOR
        );



        userRepository.save(user);



        vendorApplicationRepository.save(application);






        // Send vendor approval email

        emailService.sendVendorApprovalEmail(

                user.getEmail(),

                application.getBusinessName()

        );







        return mapResponse(
                application,
                "Vendor approved successfully."
        );

    }









    @Override
    public VendorApplicationResponse rejectVendor(
            Long applicationId
    ) {



        VendorApplication application =
                vendorApplicationRepository
                        .findById(applicationId)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"
                                )
                        );






        application.setStatus(
                ApplicationStatus.REJECTED
        );



        application.setUpdatedAt(
                LocalDateTime.now()
        );



        vendorApplicationRepository.save(application);






        // Send rejection email

        emailService.sendEmail(

                application.getEmail(),

                "Vendor Application Rejected",

                """
                Hello,

                Your vendor application has been rejected.

                Business Name:
                %s


                You can contact support for more details.

                Regards,
                Admin Team
                """
                        .formatted(
                                application.getBusinessName()
                        )

        );







        return mapResponse(
                application,
                "Vendor application rejected."
        );

    }









    private VendorApplicationResponse mapResponse(
            VendorApplication application,
            String message
    ){



        return VendorApplicationResponse.builder()

                .applicationId(
                        application.getId()
                )

                .businessName(
                        application.getBusinessName()
                )

                .ownerName(
                        application.getOwnerName()
                )

                .gstNumber(
                        application.getGstNumber()
                )

                .phone(
                        application.getPhone()
                )

                .email(
                        application.getEmail()
                )

                .address(
                        application.getAddress()
                )

                .description(
                        application.getDescription()
                )

                .status(
                        application.getStatus()
                )

                .message(
                        message
                )

                .build();

    }


}