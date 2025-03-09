package com.circuit_breaker.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(schema = "CB", name = "SELLER")
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity{

    private String name;
    private String description;
    private String category;
}
