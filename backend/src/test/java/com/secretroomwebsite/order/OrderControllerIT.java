    package com.secretroomwebsite.order;

    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.web.reactive.server.WebTestClient;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

    @SpringBootTest(webEnvironment = RANDOM_PORT)
    class OrderControllerIT {

        @Autowired
        private WebTestClient webTestClient;

        private static final String CUSTOMER_PATH = "/api/v1/orders";



        @BeforeEach
        void setUp() {
        }

        @Test
        void itShouldCreateOrder() {
            //given
            //when
            //then

        }

        @Test
        void itShouldGetAllOrders() {
            //given
            //when
            //then

        }
    }