package com.circuit_breaker.model.dto;

import com.circuit_breaker.persistence.Status;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private String id;
    private Set<ItemDto> items;
    private SellerDto seller;
    private CustomerDto customer;
    private String invoiceAddress;
    private String shippingAddress;
    private Status status;
    private BigDecimal totalPrice;
    private Long createdDate;
    private Long modifiedDate;
}
