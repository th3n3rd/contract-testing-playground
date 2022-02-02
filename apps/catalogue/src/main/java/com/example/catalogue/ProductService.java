package com.example.catalogue;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class ProductService {

    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    Product getProduct(UUID productId) {
        return productRepository.getById(productId);
    }

}
