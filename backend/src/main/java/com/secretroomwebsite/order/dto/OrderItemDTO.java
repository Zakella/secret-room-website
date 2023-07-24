package com.secretroomwebsite.order.dto;

import com.secretroomwebsite.order.Order;
import com.secretroomwebsite.product.Product;

public record OrderItemDTO(
        Product product,
        String productSize,
        Integer quantity,
        Double amount,

        Order order
) {
}