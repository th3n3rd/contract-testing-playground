package com.example.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CheckoutRequest {
    private String firstName;
    private String lastName;
    private PostalAddress postalAddress;
}
