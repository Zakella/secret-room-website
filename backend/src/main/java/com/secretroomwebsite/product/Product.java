package com.secretroomwebsite.product;

import com.secretroomwebsite.product_category.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    @ManyToOne
    @JoinColumn(name = "category_id" , nullable = false)
    private ProductCategory productCategory;

    private String name;

    private String description;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "image_url")
    private String imageURL;
    private Boolean active;

    @Column(name = "units_in_stock")
    private Integer unitsInStock;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "last_updated")
    @CreationTimestamp
    private LocalDate lastUpdated;

    //    @OneToMany()
    @Column(name = "category_id")
    @UpdateTimestamp
    private Long categoryID;


}
