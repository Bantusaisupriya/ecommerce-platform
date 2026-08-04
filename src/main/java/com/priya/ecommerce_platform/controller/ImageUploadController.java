package com.priya.ecommerce_platform.controller;


import com.priya.ecommerce_platform.dto.response.ApiResponse;
import com.priya.ecommerce_platform.service.CloudinaryService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageUploadController {



    private final CloudinaryService cloudinaryService;




    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('VENDOR','SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<String>> uploadImage(

            @RequestParam("file") MultipartFile file

    ) throws Exception {



        String imageUrl =
                cloudinaryService.uploadImage(file);



        return ResponseEntity.ok(

                ApiResponse.<String>builder()

                        .success(true)

                        .message(
                                "Image uploaded successfully"
                        )

                        .data(imageUrl)

                        .build()

        );

    }

}