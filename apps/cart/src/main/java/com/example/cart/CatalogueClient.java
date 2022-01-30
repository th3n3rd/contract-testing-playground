package com.example.cart;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "catalogue")
interface CatalogueClient {

    @GetMapping("/products/{productId}")
    ProductResponse findProduct(@PathVariable UUID productId);
}
