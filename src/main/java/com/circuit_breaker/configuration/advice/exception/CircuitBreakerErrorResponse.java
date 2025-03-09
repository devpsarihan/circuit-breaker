package com.circuit_breaker.configuration.advice.exception;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CircuitBreakerErrorResponse implements Serializable {

    private String code;
    private String message;
}
