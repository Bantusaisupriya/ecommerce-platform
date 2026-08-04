package com.priya.ecommerce_platform.service;


import com.priya.ecommerce_platform.dto.request.CouponRequest;
import com.priya.ecommerce_platform.dto.response.CouponResponse;


import java.util.List;



public interface CouponService {



    CouponResponse createCoupon(
            CouponRequest request
    );



    List<CouponResponse> getAllCoupons();



    CouponResponse getCouponById(
            Long id
    );



    CouponResponse updateCoupon(
            Long id,
            CouponRequest request
    );



    void deleteCoupon(
            Long id
    );



    CouponResponse getByCode(
            String code
    );

}