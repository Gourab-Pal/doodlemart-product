package com.doodlemart.product.catalog.enums;

public enum ProductSortField {

    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt"),
    PRICE("price");

    private final String propertyName;

    ProductSortField(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public static ProductSortField validateSortByArgument(String value) {
        for (ProductSortField sortField : ProductSortField.values()) {
            if (sortField.propertyName.equals(value)) {
                return sortField;
            }
        }

        throw new IllegalArgumentException(
                "Invalid sort field: " + value
        );
    }
}