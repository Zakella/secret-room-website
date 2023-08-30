package com.secretroomwebsite.order;

import com.secretroomwebsite.exception.ResourceNotFoundException;
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

    public OrderReview findOrderByTrackingNumber(String trackingNumber) {
        Order order = orderRepository.findByOrderTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find order by tracking number: " + trackingNumber));

        String formattedId = String.format("%05d", order.getId());

        return new OrderReview(
                formattedId,
                order.getPlacementDate(), // Assuming the placementDate is a java.util.Date
                order.getShippingAddress(),
                order.getItems(),
                order.getTotalAmountOrder(),
                order.getShippingOption(),
                order.getTotalAmount(),
                order.getCustomer()
        );
    }


    public List<Order> getOrdersByUserEmail(String email) {
        return orderRepository.findByCustomer_EmailOrderByPlacementDateDesc(email);

    }
}
