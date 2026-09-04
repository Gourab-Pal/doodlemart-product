package com.doodlemart.product.catalog.controller;

import com.doodlemart.product.catalog.dto.ProductCreateRequest;
import com.doodlemart.product.catalog.dto.ProductResponse;
import com.doodlemart.product.catalog.dto.ProductUpdateRequest;
import com.doodlemart.product.catalog.entity.Product;
import com.doodlemart.product.catalog.entity.ProductStatus;
import com.doodlemart.product.catalog.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponse> getProducts(
            @RequestParam(required = false)
            ProductStatus status,
            @RequestParam(required = false)
            String currency,
            @RequestParam(required = false)
            BigDecimal minPrice,
            @RequestParam(required = false)
            BigDecimal maxPrice,
            @RequestParam(required = false)
            String searchProductName,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size,
            @RequestParam(defaultValue = "createdAt")
            String sortBy,
            @RequestParam(defaultValue = "desc")
            String sortDirection
    ) {
        return productService.getAllProducts(
                status,
                currency,
                minPrice,
                maxPrice,
                searchProductName,
                page,
                size,
                sortBy,
                sortDirection
        );
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductCreateRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductById(@PathVariable UUID productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/{productId}/publish")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse publishProduct(@PathVariable UUID productId) {
        return productService.publishProduct(productId);
    }

    @PostMapping("/{productId}/archive")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse archiveProduct(@PathVariable UUID productId) {
        return productService.archiveProduct(productId);
    }

    @PatchMapping("/{productId}/update")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProductById(@PathVariable UUID productId, @RequestBody ProductUpdateRequest request) {
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return Map.of(
                "message", "Product deleted successfully",
                "productId", productId,
                "deleteTime", OffsetDateTime.now()
        );
    }
}
