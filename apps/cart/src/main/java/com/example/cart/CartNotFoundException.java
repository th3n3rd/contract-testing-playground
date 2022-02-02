package com.example.cart;

import lombok.Value;

import java.util.UUID;

@Value
class CartNotFoundException extends RuntimeException {
    UUID cartId;
}
