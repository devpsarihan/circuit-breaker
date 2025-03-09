package com.circuit_breaker.controller.v1.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateItemRequest implements Serializable {

    @NotNull(message = "Content ID cannot be null")
    private UUID contentId;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;

    @NotNull(message = "Price cannot be null")
    private BigDecimal price;
}

