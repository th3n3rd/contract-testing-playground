package com.example.catalogue;

import lombok.Value;

import java.util.UUID;

@Value
class ProductNotFoundException extends RuntimeException {
    UUID productId;
}
