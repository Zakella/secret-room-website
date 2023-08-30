package com.secretroomwebsite.order;

import com.secretroomwebsite.adress.Address;
import com.secretroomwebsite.customer.Customer;
import com.secretroomwebsite.shipping.Shipping;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record OrderReview(
        String orderNumber,

        Date orderDate,
        Address shippingAddress,
        List<OrderItem> items,
        Double totalAmountOrder,
        Shipping shippingOption,
        Double subtotal,
        Customer customer
) { }