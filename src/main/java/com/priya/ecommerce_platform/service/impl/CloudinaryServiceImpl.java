package com.priya.ecommerce_platform.service.impl;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.priya.ecommerce_platform.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl
        implements CloudinaryService {



    private final Cloudinary cloudinary;





    @Override
    public String uploadImage(
            MultipartFile file
    ) throws Exception {



        if (file == null || file.isEmpty()) {

            throw new RuntimeException(
                    "Image file cannot be empty"
            );

        }



        Map uploadResult =
                cloudinary.uploader()
                        .upload(

                                file.getBytes(),

                                ObjectUtils.asMap(

                                        "folder",
                                        "ecommerce-products"

                                )

                        );



        return uploadResult
                .get("secure_url")
                .toString();

    }







    @Override
    public void deleteImage(
            String publicId
    ) throws Exception {



        if(publicId == null ||
                publicId.isBlank()) {

            throw new RuntimeException(
                    "Public ID is required"
            );

        }



        cloudinary.uploader()
                .destroy(

                        publicId,

                        ObjectUtils.emptyMap()

                );

    }


}