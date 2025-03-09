package com.circuit_breaker.configuration.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ORDER_NOT_FOUND_ERROR("001", "Order is not found.", 404),
    ORDER_CREATION_FAILED_ERROR("002", "Order creation is failed.", 500);

    private final String code;
    private final String message;
    private final Integer httpStatusCode;
}
