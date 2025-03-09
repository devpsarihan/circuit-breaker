package com.circuit_breaker.persistence.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seller implements Serializable {

    private UUID id;
    private String sellerName;
}

