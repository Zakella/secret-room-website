package com.secretroomwebsite.productDao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.secretroomwebsite.product_category.ProductCategory;
import com.secretroomwebsite.product_category.ProductCategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


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

    public DataInitializer(ProductCategoryRepository productCategoryRepository, ResourceLoader resourceLoader) {
        this.productCategoryRepository = productCategoryRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override

    public void run(String... args) throws Exception {


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
    public void truncateTable(String tableName) {
        String query = String.format("TRUNCATE %s RESTART IDENTITY CASCADE", tableName);
        jdbcTemplate.execute(query);
    }


}
