package com.example.cart;

import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
class CatalogueClientFactory {

    private final FeignClientBuilder clientBuilder;

    CatalogueClientFactory(ApplicationContext context) {
        this.clientBuilder = new FeignClientBuilder(context);
    }

    CatalogueClient createClient(String targetUrl) {
        return clientBuilder
            .forType(CatalogueClient.class, "catalogue")
            .url(targetUrl)
            .build();
    }
}
