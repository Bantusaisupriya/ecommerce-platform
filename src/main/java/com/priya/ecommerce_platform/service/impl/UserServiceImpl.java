package com.priya.ecommerce_platform.service.impl;

import com.priya.ecommerce_platform.dto.request.LoginRequest;
import com.priya.ecommerce_platform.dto.request.RegisterRequest;
import com.priya.ecommerce_platform.dto.response.LoginResponse;
import com.priya.ecommerce_platform.dto.response.RegisterResponse;
import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.enums.Role;
import com.priya.ecommerce_platform.repository.UserRepository;
import com.priya.ecommerce_platform.security.jwt.JwtService;
import com.priya.ecommerce_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName().trim())
                .email(email)
                .phone(request.getPhone().trim())
                .password(passwordEncoder.encode(request.getPassword().trim()))
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(user);

        return RegisterResponse.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .message("Registration Successful")
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        String email = request.getEmail().trim().toLowerCase();
        String password = request.getPassword().trim();

        System.out.println("========== LOGIN ==========");
        System.out.println("Email Received : " + email);
        System.out.println("Password Received : " + password);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        System.out.println("Database Email : " + user.getEmail());
        System.out.println("Encoded Password : " + user.getPassword());

        boolean passwordMatch =
                passwordEncoder.matches(password, user.getPassword());

        System.out.println("Password Match : " + passwordMatch);

        if (!passwordMatch) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return LoginResponse.builder()
                .accessToken(token)
                .refreshToken("Refresh Token Coming Soon")
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}