package com.example.cart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@ResponseBody
class CartController {

    private final CartService cartService;

    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/carts")
    CartResponse createCart() {
        var cart = cartService.createCart();
        return CartResponse.from(cart);
    }

    @GetMapping("/carts/{cartId}")
    CartResponse getCart(@PathVariable UUID cartId) {
        var cart = cartService.getCart(cartId);
        return CartResponse.from(cart);
    }

    @PostMapping("/carts/{cartId}/items")
    CartResponse addItemToCart(
        @PathVariable UUID cartId,
        @RequestBody AddItemToCartRequest payload
    ) {
        var cart = cartService.addItemToCart(cartId, payload.getProductId());
        return CartResponse.from(cart);
    }

    @PostMapping("/carts/{cartId}/checkout")
    CartResponse checkoutCart(@PathVariable UUID cartId, @RequestBody CheckoutRequest payload) {
        var cart = cartService.checkoutCart(cartId, new CheckoutDetails(
            payload.getFirstName(),
            payload.getLastName(),
            payload.getPostalAddress()
        ));
        return CartResponse.from(cart);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void handleProductNotFound() {}

    @ExceptionHandler(CartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleCartNotFound() {}
}
