package com.secretroomwebsite.product;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    private String sku;

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
    private LocalDate lastUpdated;

    //    @OneToMany()
    @Column(name = "category_id")
    private Long categoryID;


}
