package com.circuit_breaker.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item implements Serializable {

    private UUID contentId;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;
}
