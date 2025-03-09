package com.circuit_breaker.controller.v1.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerRequest implements Serializable {

    @NotNull(message = "Seller ID cannot be null")
    private UUID id;

    @NotEmpty(message = "Seller name cannot be empty")
    private String sellerName;
}
