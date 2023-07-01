package com.secretroomwebsite.product_category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Product> products;

    private String description;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Brands brand;


}


