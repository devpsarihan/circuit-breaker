package com.circuit_breaker.integration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.circuit_breaker.controller.v1.request.CreateCustomerRequest;
import com.circuit_breaker.controller.v1.request.CreateItemRequest;
import com.circuit_breaker.controller.v1.request.CreateOrderRequest;
import com.circuit_breaker.controller.v1.request.CreateSellerRequest;
import com.circuit_breaker.controller.v1.request.UpdateOrderStatusRequest;
import com.circuit_breaker.integration.TestContainersConfiguration;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class OrderControllerIntegrationTest extends TestContainersConfiguration {

    private static CreateOrderRequest orderRequest;
    private static UpdateOrderStatusRequest updateOrderStatusRequest;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @BeforeEach
    public void setUp() {
        CreateItemRequest firstItem = CreateItemRequest.builder()
            .contentId(UUID.randomUUID())
            .name("Item 1")
            .description("Item 1 description")
            .quantity(1)
            .price(BigDecimal.valueOf(10.00))
            .build();
        CreateItemRequest secondItem = CreateItemRequest.builder()
            .contentId(UUID.randomUUID())
            .name("Item 2")
            .description("Item 2 description")
            .quantity(1)
            .price(BigDecimal.valueOf(20.00))
            .build();
        CreateSellerRequest seller = CreateSellerRequest.builder()
            .id(UUID.randomUUID())
            .sellerName("Seller 1")
            .build();
        CreateCustomerRequest customer = CreateCustomerRequest.builder()
            .id(UUID.randomUUID())
            .firstName("CustomerFirstName")
            .lastName("CustomerLastName")
            .email("customer@example.com")
            .build();
        orderRequest = CreateOrderRequest.builder()
            .items(Set.of(firstItem, secondItem))
            .seller(seller)
            .customer(customer)
            .invoiceAddress("Invoice Address")
            .shippingAddress("Shipping Address")
            .totalPrice(BigDecimal.valueOf(30.00))
            .build();

        updateOrderStatusRequest = UpdateOrderStatusRequest.builder().status(4).build();
    }

    @Test
    void testCreateOrder_WhenGivenOrder_ShouldReturnOrderId() {
        ResponseEntity<String> response = testRestTemplate.exchange("/v1/orders", HttpMethod.POST,
            new HttpEntity<>(orderRequest), String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateOrderStatus_WhenGivenOrderIdAndStatus_ShouldSuccess() {
        String orderId = testRestTemplate.exchange("/v1/orders", HttpMethod.POST,
            new HttpEntity<>(orderRequest), String.class).getBody();
        ResponseEntity<Void> response = testRestTemplate.exchange("/v1/orders/{orderId}", HttpMethod.PUT,
            new HttpEntity<>(updateOrderStatusRequest), Void.class, orderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateOrder_WhenCBIsHalfOpen_ShouldCBRetryAndClosed() throws InterruptedException {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("createOrder");
        circuitBreaker.transitionToOpenState();

        Thread.sleep(6000);
        assertEquals(CircuitBreaker.State.HALF_OPEN, circuitBreaker.getState());

        for (int i = 0; i < 10; i++) {
            ResponseEntity<String> response = testRestTemplate.exchange("/v1/orders", HttpMethod.POST,
                new HttpEntity<>(orderRequest), String.class);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());
    }

    @Test
    void testCreateOrder_WhenCBIsOpen_ShouldCBTriggerFallback() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("createOrder");
        circuitBreaker.transitionToOpenState();

        ResponseEntity<String> response = testRestTemplate.exchange("/v1/orders", HttpMethod.POST,
            new HttpEntity<>(orderRequest), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody()
            .contains("Fallback triggered for createOrder due to: CircuitBreaker 'createOrder' is OPEN"));
        assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());
    }

    @Test
    void testUpdateOrderStatus_WhenGivenOrderIdAndStatus_ShouldSuccessAndCBClosed() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("updateOrderStatus");

        String orderId = testRestTemplate.exchange("/v1/orders", HttpMethod.POST,
            new HttpEntity<>(orderRequest), String.class).getBody();
        ResponseEntity<Void> response = testRestTemplate.exchange("/v1/orders/{orderId}", HttpMethod.PUT,
            new HttpEntity<>(updateOrderStatusRequest), Void.class, orderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());
    }

    @Test
    void testUpdateOrderStatus_WhenCBIsHalfOpen_ShouldCBRetryAndClosed() throws InterruptedException {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("updateOrderStatus");
        circuitBreaker.transitionToOpenState();

        Thread.sleep(6000);
        assertEquals(CircuitBreaker.State.HALF_OPEN, circuitBreaker.getState());

        String orderId = testRestTemplate.exchange("/v1/orders", HttpMethod.POST,
            new HttpEntity<>(orderRequest), String.class).getBody();
        for (int i = 0; i < 10; i++) {
            ResponseEntity<Void> response = testRestTemplate.exchange("/v1/orders/{orderId}", HttpMethod.PUT,
                new HttpEntity<>(updateOrderStatusRequest), Void.class, orderId);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        Thread.sleep(1000);
        assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());
    }
}
