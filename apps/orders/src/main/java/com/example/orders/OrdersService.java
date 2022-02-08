package com.example.orders;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
class OrdersService {

    private final MessagingGateway messagingGateway;
    private final OrdersRepository ordersRepository;

    OrdersService(MessagingGateway messagingGateway, OrdersRepository ordersRepository) {
        this.messagingGateway = messagingGateway;
        this.ordersRepository = ordersRepository;
    }

    Order placeOrder(CheckoutDetails checkoutDetails) {
        var order = Order.create(checkoutDetails);
        ordersRepository.save(order);
        messagingGateway.send(OrderPlaced.from(order));
        return order;
    }

}
