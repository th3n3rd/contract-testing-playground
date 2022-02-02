package com.example.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
class Cart {
    @Id
    private UUID id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Item> items;

    static Cart create() {
        return new Cart(UUID.randomUUID(), new ArrayList<>());
    }

    void addItem(Item item) {
        items.add(item);
    }

    @Data
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    static class Item {
        private UUID productId;

        static Item of(UUID productId) {
            return new Item(productId);
        }

    }
}
