package com.secretroomwebsite.order;

import com.secretroomwebsite.order.dto.OrderRequestDTO;
import com.secretroomwebsite.order.dto.OrderRequestDtoToOrderConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderRequestDtoToOrderConverter dtoConverter;


    public OrderService(OrderRepository orderRepository, OrderRequestDtoToOrderConverter dtoConverter) {

        this.orderRepository = orderRepository;
        this.dtoConverter = dtoConverter;
    }

    public Long createOrder(OrderRequestDTO orderDTO) {
        Order order = this.dtoConverter.convert(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
