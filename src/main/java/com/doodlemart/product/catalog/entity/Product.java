package com.doodlemart.product.catalog.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "products", schema = "doodlemart_product")
public class Product {

    @Id
    private UUID id;

    @Column(name = "sku", nullable = false, unique = true, length = 100)
    private String sku;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private ProductStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    protected Product() {
    }

    public Product(
            String sku,
            String name,
            String description,
            BigDecimal price,
            String currency
    ) {
        this.id = UUID.randomUUID();
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.status = ProductStatus.DRAFT;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void updateTimestamp() {
        this.updatedAt = OffsetDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void publish() {
        if(this.status != ProductStatus.DRAFT) {
            throw new IllegalStateException("Current product can not be published. Product must be in draft state");
        }
        this.status = ProductStatus.PUBLISHED;
        this.updatedAt = OffsetDateTime.now();
    }

    public void archive() {
        if(this.status == ProductStatus.ARCHIVED) {
            throw new IllegalStateException("Current product can not be archived. Product is already archived");
        }
        this.status = ProductStatus.ARCHIVED;
        this.updatedAt = OffsetDateTime.now();
    }

    public void updateDetails(
            String description, BigDecimal price
    ) {
        this.description = description;
        this.price = price;
        this.updatedAt = OffsetDateTime.now();
    }
}
