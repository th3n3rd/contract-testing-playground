package com.example.catalogue;

import lombok.Data;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("admin")
@RestController
class AdminController {

    private final ProductRepository productRepository;

    AdminController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/products")
    ProductResponse loadProduct() {
        var product = productRepository.save(Product.create());
        return ProductResponse.from(product);
    }

}
