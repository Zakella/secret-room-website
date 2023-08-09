package com.secretroomwebsite.checkout.purchase;

import com.secretroomwebsite.AbstractTestcontainers;
import com.secretroomwebsite.TestDataProvider;
import com.secretroomwebsite.product.category.ProductCategoryRepository;
import com.secretroomwebsite.product.dao.ProductRepository;
import com.secretroomwebsite.purchase.Purchase;
import com.secretroomwebsite.purchase.PurchaseResponse;
import com.secretroomwebsite.shipping.ShippingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerIT extends AbstractTestcontainers {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    Purchase testPurchase;

    String purchasePath = "/api/v1/order";


    @BeforeEach
    void setUp() {
        TestDataProvider dataProvider = new TestDataProvider(shippingRepository, productRepository, productCategoryRepository);
        testPurchase = dataProvider.preparePurchaseForTest();
    }

    @Test
    void itShouldPlaceOrder() {


        webTestClient.post()
                .uri(this.purchasePath)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(testPurchase), Purchase.class)
                .exchange()
                .expectStatus()
                .isCreated();



    }

    @Test
        void itShouldReturnUuid() {

        String orderUuid = webTestClient.post()
                .uri(purchasePath)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(testPurchase), Purchase.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(PurchaseResponse.class)
                .getResponseBody()
                .blockFirst()
                .orderTrackingNumber();


        String orderPath = this .purchasePath + "/" + orderUuid;

        // When & Then
        webTestClient.get()
                .uri(orderPath)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.orderNumber").isEqualTo(orderUuid);

         


    }
}