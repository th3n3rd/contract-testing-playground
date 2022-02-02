package com.example.cart;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class CartService {

    private final CartRepository cartRepository;
    private final CatalogueService catalogueService;

    CartService(CartRepository cartRepository, CatalogueService catalogueService) {
        this.cartRepository = cartRepository;
        this.catalogueService = catalogueService;
    }

    Cart createCart() {
        return cartRepository.save(Cart.create());
    }

    Cart addItemToCart(UUID cartId, UUID productId) {
        var cart = getCart(cartId);
        productExistsOrThrow(productId);
        cart.addItem(Cart.Item.of(productId));
        return cartRepository.save(cart);
    }

    Cart getCart(UUID cartId) {
        return cartRepository
            .findById(cartId)
            .orElseThrow(() -> new CartNotFoundException(cartId));
    }

    private void productExistsOrThrow(UUID productId) {
        if (!catalogueService.productExists(productId)) {
            throw new ProductNotFoundException(productId);
        }
    }
}
