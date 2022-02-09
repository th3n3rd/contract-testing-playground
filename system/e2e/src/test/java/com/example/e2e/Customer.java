package com.example.e2e;

import lombok.SneakyThrows;
import retrofit2.Response;

import java.util.Objects;
import java.util.UUID;

import static com.example.e2e.Fixtures.*;

public class Customer {

    private final ECommerceApi eCommerceApi;
    private final PersonaDetails details;
    private UUID cartId;

    public Customer() {
        this.details = Personas.bob; // TODO figure out a way to use DI with constructor parameters
        this.eCommerceApi = ECommerceApiFactory.create();
    }

    @SneakyThrows
    void visitsTheShop() {
        var response = eCommerceApi.createCart().execute();
        requiresSuccessfulResponse(response);
        cartId = Objects.requireNonNull(response.body()).id;
    }

    @SneakyThrows
    void checkout() {
        var response = eCommerceApi.checkout(
            cartId,
            new CheckoutRequest(
                details.getFirstName(),
                details.getLastName(),
                details.getPostalAddress()
            )
        ).execute();
        requiresSuccessfulResponse(response);
    }

    @SneakyThrows
    void addItemToBasket(UUID productId) {
        var response = eCommerceApi
            .addItemToCart(cartId, new AddItemToCartRequest(productId))
            .execute();
        requiresSuccessfulResponse(response);
    }

    private void requiresSuccessfulResponse(Response<?> response) {
        if (!response.isSuccessful()) {
            throw new RuntimeException(String.format("There was a problem with the response: %s", response));
        }
    }

}
