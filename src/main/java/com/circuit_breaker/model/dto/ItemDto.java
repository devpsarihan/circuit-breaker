package com.circuit_breaker.model.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

    private UUID contentId;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;
}
