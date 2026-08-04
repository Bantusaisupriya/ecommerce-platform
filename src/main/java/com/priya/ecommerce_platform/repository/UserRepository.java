package com.priya.ecommerce_platform.repository;

import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /*
     * Authentication
     */
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    /*
     * Users by Role
     */
    List<User> findByRole(Role role);

    long countByRole(Role role);

    /*
     * Enabled / Disabled Users
     */
    List<User> findByEnabled(boolean enabled);

    long countByEnabled(boolean enabled);

    /*
     * Dashboard Statistics
     */
    @Query("""
            SELECT COUNT(u)
            FROM User u
            """)
    long getTotalUsers();

}