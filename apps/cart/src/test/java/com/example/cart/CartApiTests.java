package com.example.cart;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.cart.Fixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartApiTests {

    @MockBean
    private CartService cartService;

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class GetCart {

        @Test
        void notFoundWhenCartDoesNotExist() throws Exception {
            givenCartDoesNotExist();
            mockMvc
                .perform(get("/carts/{cartId}", Carts.unknown))
                .andExpect(status().isNotFound());
        }

        private void givenCartDoesNotExist() {
            given(cartService.getCart(any())).willThrow(CartNotFoundException.class);
        }
    }

    @Nested
    class AddItemToCart {
        @Test
        void notFoundWhenCartDoesNotExist() throws Exception {
            givenCartDoesNotExist();
            mockMvc
                .perform(post("/carts/{cartId}/items", Carts.unknown)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.format("{ \"productId\": \"%s\" }", Products.unknown)
                ))
                .andExpect(status().isNotFound());
        }

        @Test
        void badRequestWhenProductDoesNotExist() throws Exception {
            givenProductDoesNotExist();
            mockMvc
                .perform(post("/carts/{cartId}/items", Carts.nonEmpty)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.format("{ \"productId\": \"%s\" }", Products.unknown)
                    ))
                .andExpect(status().isBadRequest());
        }

        private void givenCartDoesNotExist() {
            given(cartService.addItemToCart(any(), any())).willThrow(CartNotFoundException.class);
        }

        private void givenProductDoesNotExist() {
            given(cartService.addItemToCart(any(), any())).willThrow(ProductNotFoundException.class);
        }
    }

}
