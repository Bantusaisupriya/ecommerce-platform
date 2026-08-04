package com.priya.ecommerce_platform.dto.response;

import com.priya.ecommerce_platform.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
    private String message;


}