package com.example.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
class PostalAddress {
    private String street;
    private String streetNumber;
    private String city;
    private String postalCode;
    private String country;
}
