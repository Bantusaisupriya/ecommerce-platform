package com.priya.ecommerce_platform.security;

import com.priya.ecommerce_platform.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                .cors(Customizer.withDefaults())

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        /*
                         * =====================================
                         * PUBLIC APIs
                         * =====================================
                         */

                        .requestMatchers(

                                // Authentication
                                "/api/auth/**",

                                // Test APIs
                                "/api/test/**",


                                // Swagger
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",

                                // Public Product APIs
                                "/api/products/**",

                                // Categories
                                "/api/categories/**",

                                // Public Reviews
                                "/api/products/*/reviews",
                                "/api/products/*/rating",

                                // Coupons
                                "/api/coupons/**"

                        ).permitAll()

                        /*
                         * =====================================
                         * CUSTOMER APIs
                         * =====================================
                         */

                        .requestMatchers(
                                "/api/customer/**"
                        ).hasRole("CUSTOMER")

                        /*
                         * =====================================
                         * CUSTOMER -> Apply Vendor
                         * =====================================
                         */

                        .requestMatchers(
                                "/api/vendor/apply"
                        ).hasRole("CUSTOMER")

                        /*
                         * =====================================
                         * VENDOR APIs
                         * =====================================
                         */

                        .requestMatchers(
                                "/api/vendor/**"
                        ).hasRole("VENDOR")

                        /*
                         * =====================================
                         * IMAGE UPLOAD
                         * =====================================
                         */

                        .requestMatchers(
                                "/api/images/upload"
                        ).hasAnyRole(
                                "VENDOR",
                                "SUPER_ADMIN"
                        )

                        /*
                         * =====================================
                         * PAYMENT
                         * =====================================
                         */

                        .requestMatchers(
                                "/api/payments/**"
                        ).authenticated()

                        /*
                         * =====================================
                         * PDF Invoice
                         * =====================================
                         */

                        .requestMatchers(
                                "/api/customer/orders/*/invoice"
                        ).hasRole("CUSTOMER")

                        /*
                         * =====================================
                         * EMAIL TEST
                         * =====================================
                         */

                        .requestMatchers(
                                "/api/test/email"
                        ).hasRole("SUPER_ADMIN")

                        /*
                         * =====================================
                         * ADMIN APIs
                         * =====================================
                         */

                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("SUPER_ADMIN")

                        /*
                         * =====================================
                         * FUTURE FEATURES
                         * =====================================
                         */
                        .requestMatchers("/api/products/**").permitAll()

                        .requestMatchers(
                                "/api/auth/forgot-password",
                                "/api/auth/reset-password",
                                "/api/auth/refresh-token",
                                "/api/auth/verify-email",
                                "/api/auth/send-otp",
                                "/api/auth/verify-otp"
                        ).permitAll()

                        .anyRequest()
                        .authenticated()

                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("https://incomparable-puppy-e480f0.netlify.app")
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

}
