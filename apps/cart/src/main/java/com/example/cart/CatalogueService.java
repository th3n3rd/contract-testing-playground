package com.example.cart;

import feign.FeignException;
import feign.RetryableException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
class CatalogueService {

    private final CatalogueClient catalogueClient;

    CatalogueService(CatalogueClient catalogueClient) {
        this.catalogueClient = catalogueClient;
    }

    boolean productExists(UUID productId) {
        try {
            var product = catalogueClient.findProduct(productId);
            return Objects.nonNull(product);
        } catch (FeignException.NotFound | RetryableException e) {
            return false;
        }
    }
}
