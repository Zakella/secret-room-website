package com.secretroomwebsite.order.dto;

import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.enums.SizeType;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemDTO implements Serializable {
    private Product product;
    private SizeType size;
    private Integer quantity;
    private Double amount;

    // getters and setters
}