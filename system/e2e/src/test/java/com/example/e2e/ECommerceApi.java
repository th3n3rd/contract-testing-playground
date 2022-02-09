package com.example.e2e;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

class ECommerceApiFactory {

    private final static String baseUrl = System.getProperty("baseUrl", "http://api.ecommerce.k8s");

    static ECommerceApi create() {
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
            .create(ECommerceApi.class);
    }
}

public interface ECommerceApi {
    @POST("/carts")
    Call<CartResponse> createCart();

    @POST("/carts/{cartId}/items")
    Call<CartResponse> addItemToCart(@Path("cartId") UUID cartId, @Body AddItemToCartRequest payload);

    @POST("/carts/{cartId}/checkout")
    Call<Void> checkout(@Path("cartId") UUID cartId, @Body CheckoutRequest payload);

    @POST("/products")
    Call<ProductResponse> loadProduct();
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CartResponse {
    UUID id;
}

@Data
@AllArgsConstructor
class AddItemToCartRequest {
    UUID productId;
}

@Data
class ProductResponse {
    UUID id;
}

@Data
@AllArgsConstructor
class CheckoutRequest {
    String firstName;
    String lastName;
    PostalAddress postalAddress;
}
