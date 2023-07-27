package com.secretroomwebsite.product.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.secretroomwebsite.product.category.ProductCategory;
import com.secretroomwebsite.product.category.ProductCategoryRepository;
import com.secretroomwebsite.shipping.Shipping;
import com.secretroomwebsite.shipping.ShippingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ProductCategoryRepository productCategoryRepository;
    private final ResourceLoader resourceLoader;
    @Autowired
    private ShippingRepository shippingRepository;

    public DataInitializer(ProductCategoryRepository productCategoryRepository,
                           ResourceLoader resourceLoader,
                           ShippingRepository shippingRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override

    public void run(String... args) throws Exception {

        deleteTable("shipping_options");
        deleteTable("size");
        deleteTable("product_image");
        deleteTable("product");
        deleteTable("product_category");

        writeProductsInDatabase();
        writeShippingOptionsInDatabase();


    }

    private void writeShippingOptionsInDatabase() throws IOException {

        Resource resource = resourceLoader.getResource("classpath:json-data/shippingData.json");

        // Create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Read JSON file and convert it to a list of ProductCategory objects
        List<Shipping> shippingOptions = Arrays.asList(objectMapper.readValue(resource.getInputStream(), Shipping[].class));
        shippingRepository.saveAll(shippingOptions);

        System.out.println("Shipping options data initialized successfully!");


    }

    private void writeProductsInDatabase() throws IOException {


//        truncateTable("product_category");

        Resource resource = resourceLoader.getResource("classpath:json-data/productsData.json");

        // Create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Read JSON file and convert it to a list of ProductCategory objects
        List<ProductCategory> productCategories = Arrays.asList(objectMapper.readValue(resource.getInputStream(), ProductCategory[].class));


        // Save each ProductCategory object in the database
        productCategoryRepository.saveAll(productCategories);

        System.out.println("Product Category data initialized successfully!");
    }


    @Transactional
    public void deleteTable(String tableName) {

        jdbcTemplate.execute(String.format("DELETE FROM %s CASCADE", tableName));

    }


}
