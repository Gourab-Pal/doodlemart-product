package com.doodlemart.product.catalog.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(UUID productId) {
        super("No product found with product id: " + productId);
    }
}
