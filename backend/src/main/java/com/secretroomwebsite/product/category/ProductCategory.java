package com.secretroomwebsite.product.category;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String nameRo;


    private String nameRu;


    private String descriptionRo;


    private String descriptionRu;


    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Product> products;


    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Brands brand;




}


