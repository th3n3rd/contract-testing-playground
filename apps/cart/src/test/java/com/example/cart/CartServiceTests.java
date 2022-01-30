package com.example.cart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({
    CartService.class,
    CatalogueService.class
})
class CartServiceTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @MockBean
    private CatalogueService catalogueService;

    @Test
    void cartNotFoundWhenRetrievingCart() {
        var unknownCartId = UUID.randomUUID();
        assertThrows(CartNotFoundException.class, () -> cartService.getCart(unknownCartId));
    }

    @Test
    void cartNotFoundWhenAddingAnItem() {
        var unknownCartId = UUID.randomUUID();
        var anyProductId = UUID.randomUUID();
        assertThrows(CartNotFoundException.class, () -> cartService.addItemToCart(
            unknownCartId,
            anyProductId
        ));
    }

    @Test
    void productNotFoundWHenAddingAnItem() {
        var cart = cartService.createCart();
        var unknownProductId = UUID.randomUUID();
        assertThrows(ProductNotFoundException.class, () -> cartService.addItemToCart(
            cart.getId(),
            unknownProductId
        ));
    }
}
