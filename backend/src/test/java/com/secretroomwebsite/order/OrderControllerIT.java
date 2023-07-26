package com.secretroomwebsite.order;

import com.secretroomwebsite.AbstractTestcontainers;
import com.secretroomwebsite.TestDataProvider;
import com.secretroomwebsite.order.dto.OrderItemDTO;
import com.secretroomwebsite.order.dto.OrderRequestDTO;
import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product.category.ProductCategory;
import com.secretroomwebsite.product.dao.ProductRepository;
import com.secretroomwebsite.shipping.Shipping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.secretroomwebsite.TestData.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.secretroomwebsite.product.category.ProductCategoryRepository;
import com.secretroomwebsite.shipping.ShippingRepository;

import java.util.List;

@SpringBootTest(webEnvironment = RANDOM_PORT)
//    @AutoConfigureWebTestClient
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderControllerIT extends AbstractTestcontainers {

    @Autowired
    private WebTestClient webTestClient;

    private static final String ORDERS_PATH = "/api/v1/orders";


    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    private OrderRequestDTO orderRequestDTO;


    @BeforeEach
    void setUp() {


        TestDataProvider dataProvider = new TestDataProvider(shippingRepository, productRepository, productCategoryRepository);
        this.orderRequestDTO = dataProvider.prepareOrderDTOForTests();

    }

    @Test
    void itShouldCreateOrder() {

        webTestClient.post()
                .uri(ORDERS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(this.orderRequestDTO), OrderRequestDTO.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(Long.class);

    }


}