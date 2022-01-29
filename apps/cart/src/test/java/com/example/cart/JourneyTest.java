package com.example.cart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class JourneyTest {

    @Autowired
    private TestRestTemplate restClient;

    @Test
    void typicalJourney() {
        var cart = createCart();

        var shirt = selectItem();
        addItemToCart(cart, shirt);

        assertItemsInCart(cart, List.of(shirt));

        var trousers = selectItem();
        addItemToCart(cart, trousers);

        assertItemsInCart(cart, List.of(shirt, trousers));
    }

    private CartResponse createCart() {
        return restClient.postForObject("/carts", null, CartResponse.class);
    }

    private CartResponse getCart(UUID cartId) {
        return restClient.getForObject("/carts/{cartId}", CartResponse.class, cartId);
    }

    private CartResponse addItemToCart(CartResponse cart, AddItemToCartRequest itemToAdd) {
        return restClient.postForObject(
            "/carts/{cartId}/items",
            itemToAdd,
            CartResponse.class,
            cart.getId()
        );
    }

    private void assertItemsInCart(CartResponse cart, List<AddItemToCartRequest> expectedItems) {
        assertThat(getCart(cart.getId()).getItems())
            .containsAll(expectedItems
                .stream()
                .map(AddItemToCartRequest::getProductId)
                .collect(Collectors.toList())
            );
    }

    private AddItemToCartRequest selectItem() {
        return new AddItemToCartRequest(UUID.randomUUID());
    }

}
