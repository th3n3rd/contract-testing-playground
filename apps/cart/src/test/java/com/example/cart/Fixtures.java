package com.example.cart;

import java.util.UUID;

class Fixtures {
    static class Carts {
        static UUID nonEmpty = UUID.fromString("6e61fced-2bbd-431c-8d92-9e9052ffe8ff");
        static UUID unknown = UUID.randomUUID();
    }
    static class Products {
        static UUID unknown = UUID.randomUUID();
        static UUID shirt = UUID.fromString("7f7b6b14-4034-429f-a286-e3946b135179");
        static UUID trousers = UUID.fromString("e6cc4361-8afa-496c-a602-900f66cf4c96");
    }
}
