package com.secretroomwebsite.order;

import com.secretroomwebsite.order.dto.OrderRequestDTO;
import com.secretroomwebsite.order.dto.OrderRequestDtoToOrderConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderRequestDtoToOrderConverter dtoConverter;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createOrderTest() {
        // Arrange
        OrderRequestDTO testOrderRequestDTO = new OrderRequestDTO();
        Order testOrder = new Order();
        testOrder.setId(1L);

        when(dtoConverter.convert(any(OrderRequestDTO.class))).thenReturn(testOrder);
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        // Act
        Long createdOrderId = orderService.createOrder(testOrderRequestDTO);

        // Assert
        assertNotNull(createdOrderId);
        assertEquals(1L, createdOrderId);
    }

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
