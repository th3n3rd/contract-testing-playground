package com.example.cart;

import lombok.Value;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Value
class CartResponse {
    UUID id;
    List<UUID> items;

    static CartResponse from(Cart cart) {
        return new CartResponse(
            cart.getId(),
            cart.getItems()
                .stream()
                .map(Cart.Item::getProductId)
                .collect(Collectors.toList())
        );
    }

}
