package com.example.cart;

import lombok.Value;

import java.util.UUID;

@Value
public class CartNotFoundException extends RuntimeException {
    UUID cartId;
}
