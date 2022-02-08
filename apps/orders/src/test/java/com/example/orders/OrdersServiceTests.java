package com.example.orders;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.orders.Fixtures.Personas.bob;
import static com.example.orders.Fixtures.Products;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Import(EcommerceMessages.class)
@SpringBootTest
class OrdersServiceTests {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Test
    void persistAnOrderWhenPlaced() {
        var order = ordersService.placeOrder(new CheckoutDetails(
            bob.getFirstName(),
            bob.getLastName(),
            bob.getPostalAddress(),
            List.of(
                new CheckoutDetails.Item(Products.shirt)
            )
        ));

        var persistedOrder = ordersRepository.getById(order.getId());

        assertThat(persistedOrder.getFirstName()).isEqualTo(bob.getFirstName());
        assertThat(persistedOrder.getLastName()).isEqualTo(bob.getLastName());
        assertThat(persistedOrder.getPostalAddress()).isEqualTo(bob.getPostalAddress());
        assertThat(persistedOrder.getLineItems()).hasSize(1);
        assertThat(persistedOrder.getLineItems().get(0).getProductId()).isEqualTo(Products.shirt);
    }
}
