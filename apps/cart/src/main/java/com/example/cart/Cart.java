package com.example.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    static Cart create() {
        return new Cart(UUID.randomUUID(), new ArrayList<>());
    }

    void addItem(Item item) {
        items.add(item);
    }

}
