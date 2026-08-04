package com.priya.ecommerce_platform.controller;


import com.priya.ecommerce_platform.dto.request.CouponRequest;
import com.priya.ecommerce_platform.dto.response.ApiResponse;
import com.priya.ecommerce_platform.dto.response.CouponResponse;
import com.priya.ecommerce_platform.service.CouponService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequiredArgsConstructor
public class CouponController {



    private final CouponService couponService;




    /*
     * ==============================
     * ADMIN COUPON APIs
     * ==============================
     */



    @PostMapping("/api/admin/coupons")
    public ApiResponse<CouponResponse> createCoupon(
            @Valid @RequestBody CouponRequest request
    ){


        return ApiResponse.success(
                "Coupon created successfully",
                couponService.createCoupon(request)
        );

    }







    @GetMapping("/api/admin/coupons")
    public ApiResponse<List<CouponResponse>> getAllCoupons(){


        return ApiResponse.success(
                "Coupons fetched successfully",
                couponService.getAllCoupons()
        );

    }







    @GetMapping("/api/admin/coupons/{id}")
    public ApiResponse<CouponResponse> getCouponById(
            @PathVariable Long id
    ){


        return ApiResponse.success(
                "Coupon fetched successfully",
                couponService.getCouponById(id)
        );

    }








    @PutMapping("/api/admin/coupons/{id}")
    public ApiResponse<CouponResponse> updateCoupon(
            @PathVariable Long id,
            @Valid @RequestBody CouponRequest request
    ){


        return ApiResponse.success(
                "Coupon updated successfully",
                couponService.updateCoupon(
                        id,
                        request
                )
        );

    }








    @DeleteMapping("/api/admin/coupons/{id}")
    public ApiResponse<String> deleteCoupon(
            @PathVariable Long id
    ){


        couponService.deleteCoupon(id);


        return ApiResponse.success(
                "Coupon deleted successfully",
                "Deleted"
        );

    }







    /*
     * ==============================
     * CUSTOMER COUPON API
     * ==============================
     */



    @GetMapping("/api/coupons/{code}")
    public ApiResponse<CouponResponse> getCouponByCode(
            @PathVariable String code
    ){


        return ApiResponse.success(
                "Coupon applied successfully",
                couponService.getByCode(code)
        );

    }



}