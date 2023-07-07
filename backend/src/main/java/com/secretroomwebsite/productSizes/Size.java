package com.secretroomwebsite.productSizes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.secretroomwebsite.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "size")
@Getter
@Setter
@ToString
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @Enumerated(EnumType.STRING)
    private SizeType sizeType;

    private boolean available;


}