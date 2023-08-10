package com.secretroomwebsite.order;

import com.secretroomwebsite.adress.Address;
import com.secretroomwebsite.customer.Customer;
import com.secretroomwebsite.shipping.Shipping;

import java.util.List;

public record OrderReview(
        String orderNumber,
        Address shippingAddress,
        List<OrderItem> items,
        Double totalAmountOrder,
        Shipping shippingOption,
        Double subtotal,
        Customer customer
) { }