package com.example.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.cart.Fixtures.Products;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Import(TestMessagingInfra.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class JourneyTest {

    @Autowired
    private TestRestTemplate restClient;

    @Autowired
    private TestMessagingInfra messagingInfra;

    @MockBean
    private CatalogueService catalogueService;

    @BeforeEach
    void setUp() {
        messagingInfra.clear();
        givenProductsExist(
            Products.shirt,
            Products.trousers
        );
    }

    @Test
    void typicalJourney() throws IOException {
        var cart = createCart();

        var shirt = selectItem(Products.shirt);
        addItemToCart(cart, shirt);

        assertItemsInCart(cart, List.of(shirt));

        var trousers = selectItem(Products.trousers);
        addItemToCart(cart, trousers);

        assertItemsInCart(cart, List.of(shirt, trousers));

        checkoutCart(cart);

        assertCheckoutStarted(cart, List.of(shirt, trousers));
    }

    private void assertCheckoutStarted(CartResponse cart, List<AddItemToCartRequest> expectedItems) throws IOException {
        var message = messagingInfra.takeMessage(CheckoutStarted.class);

        var itemsCheckedOut = message
            .getItems()
            .stream()
            .map(CheckoutStarted.Item::getProductId)
            .collect(Collectors.toList());

        assertThat(itemsCheckedOut).containsAll(
            expectedItems
                .stream()
                .map(AddItemToCartRequest::getProductId)
                .collect(Collectors.toList())
        );
    }

    private CartResponse checkoutCart(CartResponse cart) {
        return restClient.postForObject(
            "/carts/{cartId}/checkout",
            null,
            CartResponse.class,
            cart.getId()
        );
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

    private AddItemToCartRequest selectItem(UUID productId) {
        return new AddItemToCartRequest(productId);
    }

    private void givenProductsExist(UUID... products) {
        for (UUID productId : products) {
            given(catalogueService.productExists(productId)).willReturn(true);
        }
    }
}
