package com.doodlemart.product.catalog.controller;

import com.doodlemart.product.catalog.dto.ProductResponse;
import com.doodlemart.product.catalog.entity.Product;
import com.doodlemart.product.catalog.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getProducts() {
        List<ProductResponse> responses = new ArrayList<>();
        for(Product product : productService.getAllProduct()) {
            responses.add(ProductResponse.from(product));
        }
        return responses;
    }
}
