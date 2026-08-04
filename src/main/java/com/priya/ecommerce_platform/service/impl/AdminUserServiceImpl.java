package com.priya.ecommerce_platform.service.impl;

import com.priya.ecommerce_platform.dto.response.UserResponse;
import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.enums.Role;
import com.priya.ecommerce_platform.repository.UserRepository;
import com.priya.ecommerce_platform.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getCustomers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == Role.CUSTOMER)
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getVendors() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == Role.VENDOR)
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse blockUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEnabled(false);

        userRepository.save(user);

        return mapToResponse(user);
    }

    @Override
    public UserResponse unblockUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEnabled(true);

        userRepository.save(user);

        return mapToResponse(user);
    }

    @Override
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .emailVerified(user.isEmailVerified())
                .createdAt(user.getCreatedAt())
                .build();
    }
}