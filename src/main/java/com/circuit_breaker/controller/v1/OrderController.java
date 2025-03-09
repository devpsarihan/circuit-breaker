package com.circuit_breaker.controller.v1;

import com.circuit_breaker.controller.v1.request.CreateOrderRequest;
import com.circuit_breaker.controller.v1.request.UpdateOrderStatusRequest;
import com.circuit_breaker.model.dto.OrderDto;
import com.circuit_breaker.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable String orderId,
        @Valid @RequestBody UpdateOrderStatusRequest request) {
        orderService.updateOrderStatus(orderId, request.getStatus());
        return ResponseEntity.ok().build();
    }
}
