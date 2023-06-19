package com.secretroomwebsite.product_category;

import jakarta.persistence.*;

@Entity
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    @Column(name = "category_name")
    private String name;

}
