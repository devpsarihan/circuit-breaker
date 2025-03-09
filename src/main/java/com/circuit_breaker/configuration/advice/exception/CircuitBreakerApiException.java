package com.circuit_breaker.configuration.advice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CircuitBreakerException extends RuntimeException {

    private final String code;
    private final String message;
    private final Integer httpStatusCode;

    public CircuitBreakerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.httpStatusCode = errorCode.getHttpStatusCode();
    }
}
