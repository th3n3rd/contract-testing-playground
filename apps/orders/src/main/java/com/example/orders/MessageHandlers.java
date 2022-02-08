package com.example.orders;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.stream.Collectors;

@Configuration
class MessageHandlers {

    private final OrdersService ordersService;

    MessageHandlers(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Bean
    Consumer<Object> discarded() {
        return anything -> {
            // This is used as temporary solution to discard not supported messages
            // waiting for this https://github.com/spring-cloud/spring-cloud-stream/issues/2278
        };
    }

    @Bean
    Consumer<CheckoutStarted> checkoutStarted() {
        return checkoutStarted -> {
            ordersService.placeOrder(new CheckoutDetails(
                checkoutStarted.getFirstName(),
                checkoutStarted.getLastName(),
                checkoutStarted.getPostalAddress(),
                checkoutStarted.getItems()
                    .stream()
                    .map(CheckoutStarted.Item::getProductId)
                    .map(CheckoutDetails.Item::new)
                    .collect(Collectors.toList())
            ));
        };
    }

}
