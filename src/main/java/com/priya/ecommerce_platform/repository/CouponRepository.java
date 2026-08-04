package com.priya.ecommerce_platform.repository;


import com.priya.ecommerce_platform.entity.Coupon;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface CouponRepository
        extends JpaRepository<Coupon,Long> {


    Optional<Coupon> findByCode(String code);


    boolean existsByCode(String code);


}