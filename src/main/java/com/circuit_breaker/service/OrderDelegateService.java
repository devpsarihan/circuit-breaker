package com.circuit_breaker.service;

import com.circuit_breaker.configuration.advice.exception.CircuitBreakerApiException;
import com.circuit_breaker.configuration.advice.exception.ErrorCode;
import com.circuit_breaker.controller.v1.request.CreateOrderRequest;
import com.circuit_breaker.converter.OrderConverter;
import com.circuit_breaker.model.dto.OrderDto;
import com.circuit_breaker.persistence.Status;
import com.circuit_breaker.persistence.entity.Order;
import com.circuit_breaker.persistence.repository.OrderMongoRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDelegateService {

    private final OrderMongoRepository orderMongoRepository;
    private final OrderConverter orderConverter;

    @CircuitBreaker(name = "createOrder", fallbackMethod = "fallbackCreateOrder")
    @Bulkhead(name = "createOrder", type = Type.THREADPOOL, fallbackMethod = "fallbackCreateOrderBulkhead")
    @TimeLimiter(name = "createOrder", fallbackMethod = "fallbackCreateOrderTimeLimiter")
    public CompletableFuture<String> createOrder(final CreateOrderRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            Order order = orderConverter.from(request);
            return orderMongoRepository.save(order).getId().toString();
        });
    }

    public OrderDto getOrder(final String orderId) {
        Order order = orderMongoRepository.findById(new ObjectId(orderId))
            .orElseThrow(() -> new CircuitBreakerApiException(ErrorCode.ORDER_NOT_FOUND_ERROR));
        return orderConverter.from(order);
    }

    @CircuitBreaker(name = "updateOrderStatus", fallbackMethod = "fallbackUpdateOrderStatus")
    @Bulkhead(name = "updateOrderStatus", type = Type.THREADPOOL, fallbackMethod = "fallbackUpdateOrderStatusBulkhead")
    @TimeLimiter(name = "updateOrderStatus", fallbackMethod = "fallbackUpdateOrderStatusTimeLimiter")
    public CompletableFuture<Void> updateOrderStatus(final String orderId, final Integer status) {
        return CompletableFuture.supplyAsync(() -> {
            Order order = orderMongoRepository.findById(new ObjectId(orderId))
                .orElseThrow(() -> new CircuitBreakerApiException(ErrorCode.ORDER_NOT_FOUND_ERROR));
            order.setStatus(Status.getStatus(status).getCode());
            orderMongoRepository.save(order);
            return null;
        });
    }

    private CompletableFuture<String> fallbackCreateOrder(final CreateOrderRequest request, Throwable throwable) {
        log.error("Fallback triggered for createOrder due to: {}", throwable.getMessage());
        return CompletableFuture.completedFuture(
            "Fallback triggered for createOrder due to: " + throwable.getMessage());
    }

    private CompletableFuture<String> fallbackCreateOrderBulkhead(final CreateOrderRequest request,
        Throwable throwable) {
        log.error("Bulkhead fallback triggered for createOrder due to: {}", throwable.getMessage());
        return CompletableFuture.completedFuture(
            "Bulkhead fallback triggered for createOrder due to: " + throwable.getMessage());
    }

    private CompletableFuture<String> fallbackCreateOrderTimeLimiter(final CreateOrderRequest request,
        Throwable throwable) {
        log.error("TimeLimiter fallback triggered for createOrder due to: {}", throwable.getMessage());
        return CompletableFuture.completedFuture(
            "TimeLimiter fallback triggered for createOrder due to: " + throwable.getMessage());
    }

    private CompletableFuture<Void> fallbackUpdateOrderStatus(final String orderId, final Integer status,
        Throwable throwable) {
        log.error("Fallback triggered for updateOrderStatus due to: {}", throwable.getMessage());
        return CompletableFuture.completedFuture(null);
    }

    private CompletableFuture<Void> fallbackUpdateOrderStatusBulkhead(final String orderId, final Integer status,
        Throwable throwable) {
        log.error("Bulkhead fallback triggered for updateOrderStatus due to: {}", throwable.getMessage());
        return CompletableFuture.completedFuture(null);
    }

    private CompletableFuture<Void> fallbackUpdateOrderStatusTimeLimiter(final String orderId, final Integer status,
        Throwable throwable) {
        log.error("TimeLimiter fallback triggered for updateOrderStatus due to: {}", throwable.getMessage());
        return CompletableFuture.completedFuture(null);
    }
}
