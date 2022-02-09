package com.example.e2e;

import java.util.UUID;

class Fixtures {
    static class Personas {
        static PersonaDetails bob = new PersonaDetails(
            "Bob",
            "Buyer",
            new PostalAddress(
                "Fadel Overpass",
                "265",
                "Jaylanfurt",
                "07201",
                "USA"
            )
        );
    }
    static class Products {
        static UUID shirt = UUID.fromString("7f7b6b14-4034-429f-a286-e3946b135179");
    }
}
