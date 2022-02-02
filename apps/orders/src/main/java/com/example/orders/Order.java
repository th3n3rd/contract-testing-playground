package com.example.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
class Order {
    @Id
    private UUID id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<LineItem> lineItems;

    static Order create(List<LineItem> lineItems) {
        return new Order(UUID.randomUUID(), lineItems);
    }

    @Data
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    static class LineItem {
        private UUID productId;
    }
}
