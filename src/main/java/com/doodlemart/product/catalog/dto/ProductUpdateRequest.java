package com.doodlemart.product.catalog.dto;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        String description,
        BigDecimal price
) {
}
