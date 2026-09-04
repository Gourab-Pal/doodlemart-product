package com.doodlemart.product.catalog.dto;

import java.math.BigDecimal;

public record ProductCreateRequest(
        String sku,
        String name,
        String description,
        BigDecimal price,
        String currency
) {
}
