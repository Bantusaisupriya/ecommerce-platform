package com.priya.ecommerce_platform.dto.response;

import com.priya.ecommerce_platform.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String accessToken;

    private String refreshToken;

    private Long userId;

    private String fullName;

    private String email;

    private Role role;

}