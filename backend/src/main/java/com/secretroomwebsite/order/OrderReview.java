package com.secretroomwebsite.order;

import com.secretroomwebsite.adress.Address;
import com.secretroomwebsite.shipping.Shipping;

import java.math.BigDecimal;
import java.util.List;

public record OrderReview (
        String orderNumber,
        Address shippingAddress,
        List<OrderItem> items,
        Double totalAmountOrder,
        Shipping shippingOption

) { }
