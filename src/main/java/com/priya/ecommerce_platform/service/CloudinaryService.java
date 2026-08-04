package com.priya.ecommerce_platform.service;


import org.springframework.web.multipart.MultipartFile;



public interface CloudinaryService {


    /**
     * Upload image to Cloudinary
     *
     * @param file image file
     * @return uploaded image URL
     */
    String uploadImage(
            MultipartFile file
    ) throws Exception;



    /**
     * Delete image from Cloudinary
     *
     * @param publicId Cloudinary public id
     */
    void deleteImage(
            String publicId
    ) throws Exception;


}