package com.circuit_breaker.controller.v1.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderStatusRequest {

    @NotNull(message = "Status cannot be null")
    private Integer status;
}
