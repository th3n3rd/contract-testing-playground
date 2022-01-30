package com.example.cart;

import lombok.Value;

import java.util.UUID;

@Value
class ProductNotFoundException extends RuntimeException {
    UUID productId;
}
