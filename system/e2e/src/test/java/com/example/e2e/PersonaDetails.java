package com.example.e2e;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class PersonaDetails {
    private String firstName;
    private String lastName;
    private PostalAddress postalAddress;
}
