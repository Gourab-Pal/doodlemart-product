package com.doodlemart.product.catalog.controller;

import com.doodlemart.product.catalog.dto.ProductCreateRequest;
import com.doodlemart.product.catalog.dto.ProductResponse;
import com.doodlemart.product.catalog.entity.Product;
import com.doodlemart.product.catalog.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    public List<ProductResponse> getProducts() {
        List<ProductResponse> responses = new ArrayList<>();
        for(Product product : productService.getAllProducts()) {
            responses.add(ProductResponse.from(product));
        }
        return responses;
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
}
