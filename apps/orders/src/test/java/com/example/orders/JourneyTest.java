package com.example.orders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.orders.Fixtures.Products;
import static org.assertj.core.api.Assertions.assertThat;

@Import(TestMessagingInfra.class)
@SpringBootTest
class JourneyTest {

    @Autowired
    private TestMessagingInfra messagingInfra;

    @BeforeEach
    void setUp() {
        messagingInfra.clear();
    }

    @Test
    void typicalJourney() throws IOException {
        startCheckout(List.of(
            Products.shirt,
            Products.trousers
        ));

        assertOrderWasPlaced(List.of(
            Products.shirt,
            Products.trousers
        ));
    }

    private void startCheckout(List<UUID> items) {
        messagingInfra.putMessage(new CheckoutStarted(
            items
                .stream()
                .map(CheckoutStarted.Item::new)
                .collect(Collectors.toList())
        ));
    }

    private void assertOrderWasPlaced(List<UUID> items) throws IOException {
        var message = messagingInfra.takeMessage(OrderPlaced.class);

        var orderedItems = message
            .getLineItems()
            .stream()
            .map(OrderPlaced.LineItem::getProductId)
            .collect(Collectors.toList());

        assertThat(orderedItems).containsAll(items);
    }
}
