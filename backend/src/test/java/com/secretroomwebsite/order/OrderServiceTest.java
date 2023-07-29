package com.secretroomwebsite.order;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    

    @InjectMocks
    private OrderService orderService;


    @Test
    public void getAllOrdersTest() {
        // Arrange
        Order order1 = new Order();
        Order order2 = new Order();

        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(orders);

        // Act
        List<Order> returnedOrders = orderService.getAllOrders();

        // Assert
        assertNotNull(returnedOrders);
        assertEquals(2, returnedOrders.size());
    }
}
