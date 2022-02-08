package com.example.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CheckoutDetails {
    private String firstName;
    private String lastName;
    private PostalAddress postalAddress;
    private List<Item> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Item {
        private UUID productId;
    }
}
