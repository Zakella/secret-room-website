package com.secretroomwebsite.journey;

import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product_category.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.Set;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
class DataRestConfigIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void itsDisableUnsupportedActionsForProductCategory() {

        Set<Product> products = Collections.emptySet();
                ProductCategory productCategory = new ProductCategory(1L, "Test products", products);

        webTestClient.post()
                .uri("/product-category")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(productCategory), ProductCategory.class)
                .exchange()
                .expectStatus()
                .is4xxClientError();

        webTestClient.patch()
                .uri("/product-category")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(productCategory), ProductCategory.class)
                .exchange()
                .expectStatus()
                .is4xxClientError();

        webTestClient.put()
                .uri("/product-category")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(productCategory), ProductCategory.class)
                .exchange()
                .expectStatus()
                .is4xxClientError();

        webTestClient.delete()
                .uri("/product-category" + "/{id}", 111l)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is4xxClientError();


    }

    @Test
    void itsCanGetProductCategory() {

        webTestClient.get()
                .uri("product-category", 555L)
                .exchange()
                .expectStatus()
                .isOk();


    }

    @Test
    void itsDisableUnsupportedActionsForProducts() {

        Set<Product> products = Collections.emptySet();
        ProductCategory productCategory = new ProductCategory(1L, "Test products", products);

        Product product = Product.builder()
                .id(11L)
                .name("Some product")
                .productCategory(new ProductCategory(1L, "Test products", products))
                .sku(null)
                .active(true)
                .unitPrice(100.00)
                .imageURL("/url")
                .unitsInStock(1)
                .build();

        webTestClient.post()
                .uri("/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus()
                .is4xxClientError();

        webTestClient.patch()
                .uri("/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus()
                .is4xxClientError();

        webTestClient.put()
                .uri("/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus()
                .is4xxClientError();

        webTestClient.delete()
                .uri("/products" + "/{id}", 111l)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is4xxClientError();


    }

    @Test
    void itsCanGetProduct() {

        webTestClient.get()
                .uri("/products", 555L)
                .exchange()
                .expectStatus()
                .isOk();

    }



}