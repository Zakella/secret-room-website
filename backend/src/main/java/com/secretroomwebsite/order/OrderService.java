package com.secretroomwebsite.order;

import com.secretroomwebsite.order.dto.OrderRequestDTO;
import com.secretroomwebsite.order.dto.OrderRequestDtoToOrderConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        Logger logger = LoggerFactory.getLogger(Order.class);
        logger.info(String.valueOf(savedOrder));
        return savedOrder.getId();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
