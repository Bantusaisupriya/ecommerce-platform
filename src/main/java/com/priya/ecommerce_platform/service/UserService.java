package com.priya.ecommerce_platform.service;

import com.priya.ecommerce_platform.dto.request.LoginRequest;
import com.priya.ecommerce_platform.dto.request.RegisterRequest;
import com.priya.ecommerce_platform.dto.response.LoginResponse;
import com.priya.ecommerce_platform.dto.response.RegisterResponse;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}