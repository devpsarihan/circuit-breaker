package com.circuit_breaker.converter;

import com.circuit_breaker.controller.v1.request.CreateOrderRequest;
import com.circuit_breaker.model.dto.CustomerDto;
import com.circuit_breaker.model.dto.ItemDto;
import com.circuit_breaker.model.dto.OrderDto;
import com.circuit_breaker.model.dto.SellerDto;
import com.circuit_breaker.persistence.Status;
import com.circuit_breaker.persistence.entity.Customer;
import com.circuit_breaker.persistence.entity.Item;
import com.circuit_breaker.persistence.entity.Order;
import com.circuit_breaker.persistence.entity.Seller;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    public Order from(final CreateOrderRequest request) {
        Set<Item> items = request.getItems().stream().map(item -> Item.builder()
                .contentId(item.getContentId())
                .name(item.getName())
                .description(item.getDescription())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build())
            .collect(Collectors.toSet());
        return Order.builder()
            .items(items)
            .seller(Seller.builder()
                .id(request.getSeller().getId())
                .sellerName(request.getSeller().getSellerName())
                .build())
            .customer(Customer.builder()
                .firstName(request.getCustomer().getFirstName())
                .lastName(request.getCustomer().getLastName())
                .email(request.getCustomer().getEmail())
                .build())
            .invoiceAddress(request.getInvoiceAddress())
            .shippingAddress(request.getShippingAddress())
            .status(Status.CREATED.getCode())
            .totalPrice(request.getTotalPrice())
            .build();
    }

    public OrderDto from(final Order order) {
        Set<ItemDto> items = order.getItems().stream().map(item -> ItemDto.builder()
                .contentId(item.getContentId())
                .name(item.getName())
                .description(item.getDescription())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build())
            .collect(Collectors.toSet());
        return OrderDto.builder()
            .id(order.getId().toString())
            .items(items)
            .seller(SellerDto.builder()
                .id(order.getSeller().getId())
                .sellerName(order.getSeller().getSellerName())
                .build())
            .customer(CustomerDto.builder()
                .firstName(order.getCustomer().getFirstName())
                .lastName(order.getCustomer().getLastName())
                .email(order.getCustomer().getEmail())
                .build())
            .invoiceAddress(order.getInvoiceAddress())
            .shippingAddress(order.getShippingAddress())
            .status(Status.getStatus(order.getStatus()))
            .totalPrice(order.getTotalPrice())
            .createdDate(order.getCreatedDate().toEpochMilli())
            .modifiedDate(order.getModifiedDate().toEpochMilli())
            .build();
    }

}
