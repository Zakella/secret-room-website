package com.secretroomwebsite.order;

import com.secretroomwebsite.order.dto.OrderMapper;
import com.secretroomwebsite.order.dto.OrderRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;



    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Long createOrder(OrderRequestDTO orderDTO) {
        Order order = OrderMapper.INSTANCE.OrderDTOtoOrder( orderDTO );
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
