package com.secretroomwebsite.order.dto;

import com.secretroomwebsite.order.OrderStatus;
import com.secretroomwebsite.shipping.Shipping;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderRequestDTO {
    private Date placementDate;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String deliveryAddress;
    private List<OrderItemDTO> items;
    private Shipping shippingOption;
    private Double delivery;
    private Integer totalQuantity;
    private Double totalAmount;
    private Double totalAmountOrder;
    private OrderStatus status;

    // Добавьте конструктор и геттеры и сеттеры
}
