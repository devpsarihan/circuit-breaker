package com.circuit_breaker.controller.v1.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    @NotEmpty(message = "Items cannot be empty")
    @Size(max = 20, message = "The items set can contain a maximum of 20 items")
    private Set<ItemRequest> items;

    @NotNull(message = "Seller cannot be null")
    private SellerRequest seller;

    @NotNull(message = "Customer cannot be null")
    private CustomerRequest customer;

    @NotEmpty(message = "Invoice address cannot be empty")
    private String invoiceAddress;

    @NotEmpty(message = "Shipping address cannot be empty")
    private String shippingAddress;

    @NotNull(message = "Total price cannot be null")
    private BigDecimal totalPrice;
}
