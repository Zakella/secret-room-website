    package com.secretroomwebsite.order;

    import com.secretroomwebsite.TestDataProvider;
    import com.secretroomwebsite.order.dto.OrderRequestDTO;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.http.MediaType;
    import org.springframework.test.web.reactive.server.WebTestClient;
    import reactor.core.publisher.Mono;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
    import static org.springframework.web.reactive.function.BodyInserters.fromValue;

    @SpringBootTest(webEnvironment = RANDOM_PORT)
    class OrderControllerIT {

        @Autowired
        private WebTestClient webTestClient;

        private static final String ORDERS_PATH = "/api/v1/orders";



        @BeforeEach
        void setUp() {
        }

        @Test
        void itShouldCreateOrder() {

            OrderRequestDTO orderRequestDTO = TestDataProvider.createTestOrderRequestDTO();

            webTestClient.post()
                    .uri(ORDERS_PATH)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(orderRequestDTO), OrderRequestDTO.class)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .returnResult(Long.class);

        }


    }