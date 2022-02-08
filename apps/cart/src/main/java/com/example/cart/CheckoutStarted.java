package com.example.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CheckoutStarted {
    private UUID cartId;
    private String firstName;
    private String lastName;
    private PostalAddress postalAddress;
    private List<Item> items = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Item {
        private UUID productId;
    }
}
