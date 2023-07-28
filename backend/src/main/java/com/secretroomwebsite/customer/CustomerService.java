package com.secretroomwebsite.customer;

import com.secretroomwebsite.order.Order;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerService {

    public void add(Order order, Customer customer) {

        Set<Order> orders = customer.getOrders();
        if (orders == null) {
            orders = new HashSet<>();
            customer.setOrders(orders);
        }
        orders.add(order);
        order.setCustomer(customer);

        System.out.println("Order set to customer");


    }
    }
