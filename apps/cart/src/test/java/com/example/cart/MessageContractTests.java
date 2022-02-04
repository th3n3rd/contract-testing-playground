package com.example.cart;

import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.cart.Fixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@IgnoreNoPactsToVerify
@PactBroker
@Provider("cart")
@Import(TestMessagingInfra.class)
@SpringBootTest
public class MessageContractTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @MockBean
    private CatalogueService catalogueService;

    @Autowired
    private TestMessagingInfra messagingInfra;

    @BeforeEach
    void setUp(PactVerificationContext context) {
        if (Objects.nonNull(context)) { // this is needed as the context is null when there are no pacts to verify
            given(catalogueService.productExists(any())).willReturn(true);
            context.setTarget(new MessageTestTarget());
        }
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void verifyContracts(PactVerificationContext context) {
        if (Objects.nonNull(context)) {  // this is needed as the context is null when there are no pacts to verify
            context.verifyInteraction();
        }
    }

    @State("a non-empty cart with id 6e61fced-2bbd-431c-8d92-9e9052ffe8ff is checked out")
    void nonEmptyCartExists() {
        var cartId = UUID.fromString("6e61fced-2bbd-431c-8d92-9e9052ffe8ff");
        cartRepository.save(new Cart(
            cartId,
            List.of(
                Cart.Item.of(Products.shirt),
                Cart.Item.of(Products.trousers)
            )
        ));
        cartService.checkoutCart(cartId);
    }

    @PactVerifyProvider("a checkout started event")
    String checkoutStarted() {
        var message = messagingInfra.takeMessage();
        return new String(message.getPayload());
    }

}
