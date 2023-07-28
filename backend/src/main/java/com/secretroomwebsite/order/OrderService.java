package com.secretroomwebsite.order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void addItems(Order order, OrderItem item) {

        List<OrderItem> items = order.getItems();
        if (items == null) {
            items = new ArrayList<>();
            order.setItems(items);
        }
        items.add(item);
    }
}
