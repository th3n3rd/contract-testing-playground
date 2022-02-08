package com.example.orders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.orders.Fixtures.Personas.*;
import static com.example.orders.Fixtures.Products;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Import(EcommerceMessages.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class JourneyTest {

    @Autowired
    private EcommerceMessages eCommerceMessages;

    @BeforeEach
    void setUp() {
        eCommerceMessages.clear();
    }

    @Test
    void typicalJourney() {
        startCheckout(
            bob.getFirstName(),
            bob.getLastName(),
            bob.getPostalAddress(),
            List.of(
                Products.shirt,
                Products.trousers
            )
        );

        assertOrderWasPlaced(
            bob.getFirstName(),
            bob.getLastName(),
            bob.getPostalAddress(),
            List.of(
                Products.shirt,
                Products.trousers
            )
        );
    }

    private void startCheckout(
        String firstName,
        String lastName,
        PostalAddress postalAddress,
        List<UUID> items
    ) {
        eCommerceMessages.putMessage(
            new CheckoutStarted(
                firstName,
                lastName,
                postalAddress,
                items
                    .stream()
                    .map(CheckoutStarted.Item::new)
                    .collect(Collectors.toList())
            )
        );
    }

    private void assertOrderWasPlaced(
        String firstName,
        String lastName,
        PostalAddress postalAddress,
        List<UUID> items
    ) {
        var message = eCommerceMessages.takeMessage(OrderPlaced.class);

        assertThat(message.getFirstName()).isEqualTo(firstName);
        assertThat(message.getLastName()).isEqualTo(lastName);
        assertThat(message.getPostalAddress()).isEqualTo(postalAddress);

        var orderedItems = message
            .getLineItems()
            .stream()
            .map(OrderPlaced.LineItem::getProductId)
            .collect(Collectors.toList());

        assertThat(orderedItems).containsAll(items);
    }
}
