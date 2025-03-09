package com.circuit_breaker.service;

import com.circuit_breaker.configuration.advice.exception.CircuitBreakerApiException;
import com.circuit_breaker.configuration.advice.exception.ErrorCode;
import com.circuit_breaker.controller.v1.request.CreateOrderRequest;
import com.circuit_breaker.model.dto.OrderDto;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderDelegateService orderDelegateService;

    public String createOrder(final CreateOrderRequest request) {
        try {
            return orderDelegateService.createOrder(request).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new CircuitBreakerApiException(ErrorCode.ORDER_CREATION_FAILED_ERROR);
        }
    }

    public OrderDto getOrder(final String orderId) {
        return orderDelegateService.getOrder(orderId);
    }

    public void updateOrderStatus(final String orderId, final Integer status) {
        orderDelegateService.updateOrderStatus(orderId, status);
    }
}
