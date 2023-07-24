package com.secretroomwebsite.shipping;

import com.secretroomwebsite.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "shipping_options")
@Getter
@Setter
@AllArgsConstructor

public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "description")
    private String description;

    @Column(name = "expected_delivery_from")
    private Integer expectedDeliveryFrom;

    @Column(name = "expected_delivery_to")
    private Integer expectedDeliveryTo;

    public Shipping() {}

    public Shipping(String name, Double cost, String description, Integer expectedDeliveryFrom, Integer expectedDeliveryTo) {
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.expectedDeliveryFrom = expectedDeliveryFrom;
        this.expectedDeliveryTo = expectedDeliveryTo;


    }


    // getters and setters...
}
