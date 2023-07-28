package com.secretroomwebsite.checkout.purchase;

import com.secretroomwebsite.AbstractTestcontainers;
import com.secretroomwebsite.TestDataProvider;
import com.secretroomwebsite.product.category.ProductCategoryRepository;
import com.secretroomwebsite.product.dao.ProductRepository;
import com.secretroomwebsite.shipping.ShippingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.secretroomwebsite.TestData.getTestPurchase;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CheckoutControllerIT extends AbstractTestcontainers {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    Purchase testPurchase;


    @BeforeEach
    void setUp() {
        TestDataProvider dataProvider = new TestDataProvider(shippingRepository, productRepository, productCategoryRepository);
        testPurchase = dataProvider.preparePurchaseForTest();
    }

    @Test
    void itShouldPlaceOrder() {
        //Given


        String purchasePath = "/api/v1/checkout";

        //When

        webTestClient.post()
                .uri(purchasePath)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(testPurchase), Purchase.class)
                .exchange()
                .expectStatus()
                .isCreated();

        //Then

    }
}