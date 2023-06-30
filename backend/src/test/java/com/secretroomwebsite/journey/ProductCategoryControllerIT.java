package com.secretroomwebsite.journey;

import com.secretroomwebsite.product_category.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureWebTestClient
public class ProductCategoryControllerIT {


    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetAllProductCategories() {
        webTestClient.get()
                .uri("/api/product-categories")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ProductCategory.class);
    }

    @Test
    public void testGetProductCategoryById() {
        Long categoryId = 1L; // Замените на существующий ID категории

        webTestClient.get()
                .uri("/api/product-categories/{id}", categoryId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ProductCategory.class);
    }

    @Test
    public void testGetProductCategoryById_NotFound() {
        Long categoryId = 999L; // Замените на несуществующий ID категории

        webTestClient.get()
                .uri("/api/product-categories/{id}", categoryId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testGetProductCategoryById_InvalidId() {
        String invalidId = "abc";

        webTestClient.get()
                .uri("/api/product-categories/{id}", invalidId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testGetProductCategoryById_ServerError() {
        Long categoryId = 1L;
        webTestClient.get()
                .uri("/api/product-categories/{id}", categoryId)
                .header("X-Error", "true")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
