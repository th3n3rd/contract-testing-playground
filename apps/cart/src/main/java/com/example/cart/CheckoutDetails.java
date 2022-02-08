package com.example.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CheckoutDetails {
    private String firstName;
    private String lastName;
    private PostalAddress postalAddress;
}
