package com.example.cart;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;

    CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    Cart createCart() {
        return cartRepository.save(Cart.create());
    }

    Cart addItemToCart(UUID cartId, UUID productId) {
        existOrThrow(cartId);
        var cart = cartRepository.getById(cartId);
        cart.addItem(Item.of(productId));
        return cartRepository.save(cart);
    }

    Cart getCart(UUID cartId) {
        existOrThrow(cartId);
        return cartRepository.getById(cartId);
    }

    private void existOrThrow(UUID cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new CartNotFoundException(cartId);
        }
    }
}
