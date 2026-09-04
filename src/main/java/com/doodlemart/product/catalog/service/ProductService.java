package com.doodlemart.product.catalog.service;

import com.doodlemart.product.catalog.dto.ProductCreateRequest;
import com.doodlemart.product.catalog.dto.ProductResponse;
import com.doodlemart.product.catalog.dto.ProductUpdateRequest;
import com.doodlemart.product.catalog.entity.Product;
import com.doodlemart.product.catalog.entity.ProductStatus;
import com.doodlemart.product.catalog.enums.ProductSortField;
import com.doodlemart.product.catalog.exception.ProductNotFoundException;
import com.doodlemart.product.catalog.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponse> getAllProducts(
            ProductStatus status,
            String currency,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String searchProductName,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        String lowerCasedSearchProductName = "";
        if (searchProductName != null) {
            lowerCasedSearchProductName = searchProductName.toLowerCase(Locale.ROOT);
        }

        ProductSortField.validateSortByArgument(sortBy);
        if(!"asc".equals(sortDirection) && !"desc".equals(sortDirection)) {
            throw new IllegalArgumentException("Invalid sort direction passed: " + sortDirection);
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = productRepository.findWithFilters(
                status,
                currency,
                minPrice,
                maxPrice,
                lowerCasedSearchProductName,
                pageable
        );
        return productPage.map(product -> ProductResponse.from(product));
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
        Product product = productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException(productId));
        return ProductResponse.from(product);
    }

    @Transactional
    public ProductResponse publishProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
        product.publish();
        Product savedProduct = productRepository.save(product);
        return ProductResponse.from(savedProduct);
    }

    @Transactional
    public ProductResponse archiveProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException(productId));
        product.archive();
        Product savedProduct = productRepository.save(product);
        return ProductResponse.from(savedProduct);
    }

    @Transactional
    public ProductResponse updateProduct(UUID productId, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
        product.updateDetails(productUpdateRequest.description(), productUpdateRequest.price());
        Product savedProduct = productRepository.save(product);
        return ProductResponse.from(savedProduct);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }
}
