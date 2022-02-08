package com.example.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
class Order {
    @Id
    private UUID id;

    private String firstName;
    private String lastName;

    @Embedded
    private PostalAddress postalAddress;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<LineItem> lineItems;

    static Order create(CheckoutDetails checkoutDetails) {
        return new Order(
            UUID.randomUUID(),
            checkoutDetails.getFirstName(),
            checkoutDetails.getLastName(),
            checkoutDetails.getPostalAddress(),
            checkoutDetails.getItems()
                .stream()
                .map(CheckoutDetails.Item::getProductId)
                .map(Order.LineItem::new)
                .collect(Collectors.toList())
        );
    }

    @Data
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    static class LineItem {
        private UUID productId;
    }
}
