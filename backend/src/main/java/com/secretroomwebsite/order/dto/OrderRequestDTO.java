package com.secretroomwebsite.order.dto;

import com.secretroomwebsite.order.OrderStatus;

import java.util.List;

public record OrderRequestDTO (
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String deliveryAddress,
        Long shippingOptionId,
        List<OrderItemDTO> items,
        OrderStatus status,
        Integer totalQuantity,
        Double totalAmount,
        Double totalAmountOrder
) {
    public OrderRequestDTO {
        status = status != null ? status : OrderStatus.PENDING;
    }
}