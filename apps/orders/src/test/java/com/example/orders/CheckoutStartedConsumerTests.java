package com.example.orders;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.function.Consumer;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static com.example.orders.Fixtures.Personas.bob;
import static com.example.orders.Fixtures.Products;
import static org.mockito.BDDMockito.then;

@PactTestFor(pactVersion = PactSpecVersion.V3, providerType = ProviderType.ASYNCH)
@ExtendWith(PactConsumerTestExt.class)
@Import(EcommerceMessages.class)
@SpringBootTest
class CheckoutStartedConsumerTests {

    @Autowired
    private EcommerceMessages messageInfra;

    @MockBean
    private Consumer<CheckoutStarted> checkoutStartedHandler;

    @Pact(consumer = "orders", provider = "cart")
    MessagePact checkoutStarted(MessagePactBuilder builder) {
        return builder
            .given("a non-empty cart with id 6e61fced-2bbd-431c-8d92-9e9052ffe8ff is checked out")
            .expectsToReceive("a checkout started event")
            .withMetadata(metadata -> {
                metadata.add("type", "CheckoutStarted");
            })
            .withContent(
                newJsonBody(body -> {
                    body.stringType("firstName", bob.getFirstName());
                    body.stringType("lastName", bob.getLastName());
                    body.object("postalAddress", address -> {
                        address.stringType("street", bob.getPostalAddress().getStreet());
                        address.stringType("streetNumber", bob.getPostalAddress().getStreetNumber());
                        address.stringType("city", bob.getPostalAddress().getCity());
                        address.stringType("postalCode", bob.getPostalAddress().getPostalCode());
                        address.stringType("country", bob.getPostalAddress().getCountry());
                    });
                    body.eachLike("items", item -> {
                       item.uuid("productId", Products.shirt);
                    });
                }).build()
            )
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "checkoutStarted")
    void receiveCheckoutStarted(List<Message> messages) {
        for (var message : messages) {
            messageInfra.putMessage(message.getContents().valueAsString(), "CheckoutStarted");
            then(checkoutStartedHandler).should().accept(
                new CheckoutStarted(
                    bob.getFirstName(),
                    bob.getLastName(),
                    bob.getPostalAddress(),
                    List.of(new CheckoutStarted.Item(Products.shirt))
                )
            );
        }
    }
}
