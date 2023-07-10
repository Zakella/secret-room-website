package com.secretroomwebsite.product;

import com.fasterxml.jackson.annotation.*;
import com.secretroomwebsite.enums.Brands;
import com.secretroomwebsite.productImages.ProductImage;
import com.secretroomwebsite.productSizes.Size;
import com.secretroomwebsite.product_category.ProductCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "product")
@Builder
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    @JsonIgnoreProperties("products")
    private ProductCategory productCategory;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Brands brand;


    private String shortDescription;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "image_url")
    private String imageURL;
    private Boolean active;

    @Column(name = "units_in_stock")
    private Integer unitsInStock;

    @Column(name = "date_created")
    @CreationTimestamp
    private LocalDate dateCreated;

    @Column(name = "last_updated")
    @UpdateTimestamp
    @JsonIgnore
    private LocalDate lastUpdated;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonProperty("productImages")
    @ToString.Exclude
    private List<ProductImage> images ;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Size> sizes;

}
