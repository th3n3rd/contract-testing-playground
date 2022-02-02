package com.example.cart;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
class CartService {

    private final MessagingGateway messagingGateway;
    private final CartRepository cartRepository;
    private final CatalogueService catalogueService;

    CartService(
        MessagingGateway messagingGateway,
        CartRepository cartRepository,
        CatalogueService catalogueService
    ) {
        this.messagingGateway = messagingGateway;
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

    Cart checkoutCart(UUID cartId) {
        var cart = getCart(cartId);
        messagingGateway.send(new CheckoutStarted(
            cart.getId(),
            cart.getItems()
                .stream()
                .map(Cart.Item::getProductId)
                .map(CheckoutStarted.Item::new)
                .collect(Collectors.toList())
        ));
        return cart;
    }

    private void productExistsOrThrow(UUID productId) {
        if (!catalogueService.productExists(productId)) {
            throw new ProductNotFoundException(productId);
        }
    }

}
