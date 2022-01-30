package com.example.cart;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static com.example.cart.Fixtures.Products;

@PactTestFor(pactVersion = PactSpecVersion.V3)
@ExtendWith(PactConsumerTestExt.class)
@Import(CatalogueClientFactory.class)
@SpringBootTest
class CatalogueClientTests {

    @Autowired
    private CatalogueClientFactory catalogueClientFactory;

    @Pact(consumer = "cart", provider = "catalogue")
    RequestResponsePact productExists(PactDslWithProvider builder) {
        return builder
            .given(String.format("a product with id %s exists", Products.shirt))
            .uponReceiving(String.format("a request to get a product with id %s", Products.shirt))
            .method("GET")
            .path(String.format("/products/%s", Products.shirt))
            .willRespondWith()
            .status(200)
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "productExists")
    void retrieveExistingProduct(MockServer mockServer) {
        var client = catalogueClientFactory.createClient(mockServer.getUrl());
        client.findProduct(Products.shirt);
    }
}
