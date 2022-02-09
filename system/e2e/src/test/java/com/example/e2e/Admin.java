package com.example.e2e;

import lombok.SneakyThrows;
import retrofit2.Response;

import java.util.Objects;
import java.util.UUID;

public class Admin {

    private final ECommerceApi eCommerceApi;

    public Admin() {
        this.eCommerceApi = ECommerceApiFactory.create();
    }

    @SneakyThrows
    UUID loadProduct() {
        var response = eCommerceApi.loadProduct().execute();
        requiresSuccessfulResponse(response);
        return Objects.requireNonNull(response.body()).id;
    }

    private void requiresSuccessfulResponse(Response<?> response) {
        if (!response.isSuccessful()) {
            throw new RuntimeException(String.format("There was a problem with the response: %s", response));
        }
    }
}
