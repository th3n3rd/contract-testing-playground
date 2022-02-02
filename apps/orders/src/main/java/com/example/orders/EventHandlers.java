package com.example.orders;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.stream.Collectors;

@Configuration
class EventHandlers {

    private final OrdersService ordersService;

    EventHandlers(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Bean
    Consumer<CheckoutStarted> checkoutStartedHandler() {
        return checkoutStarted -> {
            ordersService.placeOrder(new Cart(
                checkoutStarted.getItems()
                    .stream()
                    .map(CheckoutStarted.Item::getProductId)
                    .map(Cart.Item::new)
                    .collect(Collectors.toList())
            ));
        };
    }

}
