package com.priya.ecommerce_platform.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistRequest {


    @NotNull(message = "Product ID is required")
    private Long productId;

}