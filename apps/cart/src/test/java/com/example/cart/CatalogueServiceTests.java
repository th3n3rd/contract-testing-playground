package com.example.cart;

import feign.FeignException;
import feign.RetryableException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class CatalogueServiceTests {

    @MockBean
    private CatalogueClient catalogueClient;

    @Autowired
    private CatalogueService catalogueService;

    @Test
    void productExists() {
        var knownProductId = UUID.randomUUID();
        givenProductExists(knownProductId);
        assertThat(catalogueService.productExists(knownProductId)).isTrue();
    }

    @Test
    void productDoesNotExists() {
        var unknownProductId = UUID.randomUUID();
        givenProductDoesNotExists(unknownProductId);
        assertThat(catalogueService.productExists(unknownProductId)).isFalse();
    }

    @Test
    void productDoesNotExistsWhenCatalogIsNotReachable() {
        var anyProductId = UUID.randomUUID();
        givenCatalogueIsNotReachable();
        assertThat(catalogueService.productExists(anyProductId)).isFalse();
    }

    private void givenCatalogueIsNotReachable() {
        given(catalogueClient.findProduct(any())).willThrow(RetryableException.class);
    }

    private void givenProductDoesNotExists(UUID productId) {
        given(catalogueClient.findProduct(productId)).willThrow(FeignException.NotFound.class);
    }

    private void givenProductExists(UUID productId) {
        given(catalogueClient.findProduct(productId)).willReturn(new ProductResponse());
    }
}
