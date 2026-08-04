package com.priya.ecommerce_platform.service.impl;


import com.priya.ecommerce_platform.dto.request.CouponRequest;
import com.priya.ecommerce_platform.dto.response.CouponResponse;
import com.priya.ecommerce_platform.entity.Coupon;
import com.priya.ecommerce_platform.repository.CouponRepository;
import com.priya.ecommerce_platform.service.CouponService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements CouponService {



    private final CouponRepository couponRepository;




    @Override
    public CouponResponse createCoupon(
            CouponRequest request
    ) {


        if(couponRepository.existsByCode(request.getCode())){

            throw new RuntimeException(
                    "Coupon code already exists"
            );

        }



        Coupon coupon =
                Coupon.builder()
                        .code(
                                request.getCode()
                                        .toUpperCase()
                        )
                        .description(
                                request.getDescription()
                        )
                        .discountType(
                                request.getDiscountType()
                        )
                        .discountValue(
                                request.getDiscountValue()
                        )
                        .minimumAmount(
                                request.getMinimumAmount()
                        )
                        .maximumDiscount(
                                request.getMaximumDiscount()
                        )
                        .usageLimit(
                                request.getUsageLimit()
                        )
                        .expiryDate(
                                request.getExpiryDate()
                        )
                        .active(true)
                        .usedCount(0)
                        .build();



        return mapToResponse(
                couponRepository.save(coupon)
        );

    }







    @Override
    @Transactional(readOnly = true)
    public List<CouponResponse> getAllCoupons(){

        return couponRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

    }







    @Override
    @Transactional(readOnly = true)
    public CouponResponse getCouponById(
            Long id
    ){


        Coupon coupon =
                couponRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Coupon not found"
                                )
                        );


        return mapToResponse(coupon);

    }







    @Override
    public CouponResponse updateCoupon(
            Long id,
            CouponRequest request
    ){


        Coupon coupon =
                couponRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Coupon not found"
                                )
                        );



        coupon.setDescription(
                request.getDescription()
        );


        coupon.setDiscountType(
                request.getDiscountType()
        );


        coupon.setDiscountValue(
                request.getDiscountValue()
        );


        coupon.setMinimumAmount(
                request.getMinimumAmount()
        );


        coupon.setMaximumDiscount(
                request.getMaximumDiscount()
        );


        coupon.setUsageLimit(
                request.getUsageLimit()
        );


        coupon.setExpiryDate(
                request.getExpiryDate()
        );



        return mapToResponse(
                couponRepository.save(coupon)
        );

    }







    @Override
    public void deleteCoupon(
            Long id
    ){

        Coupon coupon =
                couponRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Coupon not found"
                                )
                        );


        couponRepository.delete(coupon);

    }








    @Override
    @Transactional(readOnly = true)
    public CouponResponse getByCode(
            String code
    ){


        Coupon coupon =
                couponRepository.findByCode(
                                code.toUpperCase()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid coupon code"
                                )
                        );



        if(!coupon.isActive()){

            throw new RuntimeException(
                    "Coupon is inactive"
            );

        }



        return mapToResponse(coupon);

    }







    private CouponResponse mapToResponse(
            Coupon coupon
    ){


        return CouponResponse.builder()

                .id(
                        coupon.getId()
                )

                .code(
                        coupon.getCode()
                )

                .description(
                        coupon.getDescription()
                )

                .discountType(
                        coupon.getDiscountType()
                )

                .discountValue(
                        coupon.getDiscountValue()
                )

                .minimumAmount(
                        coupon.getMinimumAmount()
                )

                .maximumDiscount(
                        coupon.getMaximumDiscount()
                )

                .usageLimit(
                        coupon.getUsageLimit()
                )

                .usedCount(
                        coupon.getUsedCount()
                )

                .active(
                        coupon.isActive()
                )

                .expiryDate(
                        coupon.getExpiryDate()
                )

                .createdAt(
                        coupon.getCreatedAt()
                )

                .build();

    }


}