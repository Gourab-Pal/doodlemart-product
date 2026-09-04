package com.doodlemart.product.catalog.service;

import com.doodlemart.product.catalog.dto.ProductCreateRequest;
import com.doodlemart.product.catalog.dto.ProductResponse;
import com.doodlemart.product.catalog.entity.Product;
import com.doodlemart.product.catalog.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductResponse createProduct(ProductCreateRequest request) {
        Product product = new Product(
                request.sku(),
                request.name(),
                request.description(),
                request.price(),
                request.currency()
        );

        Product savedProduct = productRepository.save(product);
        return ProductResponse.from(savedProduct);
    }

    public ProductResponse getProductById(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        return ProductResponse.from(product);
    }

    @Transactional
    public ProductResponse publishProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.publish();
        Product savedProduct = productRepository.save(product);
        return ProductResponse.from(savedProduct);
    }
}
