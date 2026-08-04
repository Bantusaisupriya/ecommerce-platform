package com.priya.ecommerce_platform.entity;


import com.priya.ecommerce_platform.enums.DiscountType;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;



@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(nullable = false, unique = true)
    private String code;



    @Column(nullable = false)
    private String description;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;



    @Column(nullable = false)
    private BigDecimal discountValue;



    /*
     * Minimum cart amount required
     */
    private BigDecimal minimumAmount;



    /*
     * Maximum discount limit
     */
    private BigDecimal maximumDiscount;



    @Column(nullable = false)
    private Integer usageLimit;



    @Column(nullable = false)
    private Integer usedCount = 0;



    @Column(nullable = false)
    private boolean active = true;



    private LocalDateTime expiryDate;



    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;



    private LocalDateTime updatedAt;



    @PrePersist
    public void onCreate(){

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

    }



    @PreUpdate
    public void onUpdate(){

        updatedAt = LocalDateTime.now();

    }

}