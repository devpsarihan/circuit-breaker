package com.circuit_breaker.persistence;

import java.util.Arrays;
import java.util.InputMismatchException;
import lombok.Getter;

@Getter
public enum Status {

    CREATED(0),
    PENDING(1),
    CONFIRMED(2),
    FULFILLMENT(3),
    CANCELLED(4),
    EXPIRED(5),
    DECLINED(6),
    REFUNDED(7),
    SHIPPED(8),
    FAILED(9),
    COMPLETED(10);

    private final Integer code;

    Status(Integer code) {
        this.code = code;
    }

    public static Status getStatus(final Integer code) {
        return Arrays.stream(values())
            .filter(status -> status.getCode().equals(code))
            .findFirst()
            .orElseThrow(() -> new InputMismatchException("Status not found by code " + code));
    }
}
