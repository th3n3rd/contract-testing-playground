package com.example.e2e;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class PostalAddress {
    private String street;
    private String streetNumber;
    private String city;
    private String postalCode;
    private String country;
}
