package com.priya.ecommerce_platform.repository;

import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.entity.VendorApplication;
import com.priya.ecommerce_platform.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendorApplicationRepository extends JpaRepository<VendorApplication, Long> {

    Optional<VendorApplication> findByUser(User user);

    boolean existsByUser(User user);

    List<VendorApplication> findByStatus(ApplicationStatus status);

    long countByStatus(ApplicationStatus status);
}