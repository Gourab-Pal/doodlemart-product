package com.doodlemart.product.catalog.repository;

import com.doodlemart.product.catalog.entity.Product;
import com.doodlemart.product.catalog.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("""
        SELECT product
        FROM Product product
        WHERE (:status IS NULL OR product.status = :status) 
                AND (:currency IS NULL OR product.currency = :currency)
                AND (:minPrice IS NULL OR product.price >= :minPrice)
                AND (:maxPrice IS NULL OR product.price <= :maxPrice)
                AND (:searchProductName = '' OR LOWER(product.name) LIKE CONCAT('%', :searchProductName, '%'))
        """)
    List<Product> findWithFilters(
            @Param("status") ProductStatus status,
            @Param("currency") String  currency,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("searchProductName") String searchProductName
    );
}
