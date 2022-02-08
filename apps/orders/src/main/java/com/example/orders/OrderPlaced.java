package com.example.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
class OrderPlaced {
    private UUID orderId;
    private String firstName;
    private String lastName;
    private PostalAddress postalAddress;
    private List<LineItem> lineItems;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class LineItem {
        private UUID productId;
    }

    static OrderPlaced from(Order order) {
        return new OrderPlaced(
            order.getId(),
            order.getFirstName(),
            order.getLastName(),
            order.getPostalAddress(),
            order.getLineItems()
                .stream()
                .map(Order.LineItem::getProductId)
                .map(LineItem::new)
                .collect(Collectors.toList())
        );
    }
}
