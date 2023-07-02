package com.secretroomwebsite.product;

import com.secretroomwebsite.AbstractTestcontainers;
import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.product_category.ProductCategory;
import com.secretroomwebsite.product_category.ProductCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest  extends AbstractTestcontainers {

    @Autowired
    private ProductRepository underTest;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @BeforeEach
    void setUp() {


    }

    @Test
    void itShouldFindAllByProductCategory_Id() {
        //given
        //when
        //then

    }

    @Test
    void itShouldFindByBrand() {

        ProductCategory category = new ProductCategory();
        category.setDescription("Category A");
        category.setBrand(Brands.VictoriasSecret);
        category.setCategoryName("Category A");
        category.setImageUrl("assets/tests");
        productCategoryRepository.save(category);



        Product product = Product.builder()
                .sku("SKU001")
                .productCategory(category)
                .name("Product 1")
                .description("Description 1")
                .brand(Brands.VictoriasSecret)
                .shortDescription("Short Description 1")
                .unitPrice(10.0)
                .imageURL("image1.jpg")
                .active(true)
                .unitsInStock(100)
                .dateCreated(LocalDate.now())
                .build();

        underTest.save(product);


    }


}