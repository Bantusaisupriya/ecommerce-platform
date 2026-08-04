package com.priya.ecommerce_platform.dto.response;

import com.priya.ecommerce_platform.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private Role role;

    private boolean enabled;

    private boolean emailVerified;

    private LocalDateTime createdAt;
}