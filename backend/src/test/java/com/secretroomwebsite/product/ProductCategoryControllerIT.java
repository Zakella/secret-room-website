package com.secretroomwebsite.product;

import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product_category.ProductCategory;
import com.secretroomwebsite.product_category.ProductCategoryController;
import com.secretroomwebsite.product_category.ProductGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ProductCategoryControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    private static final String CUSTOMER_PATH = "/api/v1/product-category";


    @Test
    public void testGetAllProductCategories() {

         webTestClient.get()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();


    }

    @Test
    public void testGetProductCategoryByID() {

        webTestClient.get()
                .uri(CUSTOMER_PATH + "/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();


    }


}
