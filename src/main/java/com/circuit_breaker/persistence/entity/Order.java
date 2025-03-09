package com.circuit_breaker.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "orders")
public class Order implements Serializable {

    @Id
    @Field("_id")
    private ObjectId id;
    private Set<Item> items;
    private Seller seller;
    private Customer customer;
    private String invoiceAddress;
    private String shippingAddress;
    private Integer status;
    private BigDecimal totalPrice;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant modifiedDate;
}
