package com.secretroomwebsite.order;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.secretroomwebsite.TestData.getTestOrder;
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
    public void testGetAllOrdersWhenCalledThenReturnAllOrders() {
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

    @Test
    public void testAddItemsWhenCalledThenAddItemToOrder() {
        // Arrange
        Order order = new Order();
        OrderItem item = new OrderItem();

        // Act
        orderService.addItems(order, item);

        // Assert
        assertNotNull(order.getItems());
        assertTrue(order.getItems().contains(item));
    }

    @Test
    public void testFindOrderByTrackingNumberWhenOrderExists() {
        // Arrange
        String trackingNumber = "12345";
        Order order = new Order();
        order.setId(1L);
        order.setPlacementDate(new Date());
        // Set other properties of the order

        when(orderRepository.findByOrderTrackingNumber(trackingNumber)).thenReturn(Optional.of(order));

        // Act
        OrderReview orderReview = orderService.findOrderByTrackingNumber(trackingNumber);

        // Assert
        assertNotNull(orderReview);
        assertEquals("00001", orderReview.orderNumber());
        // Assert other properties of the order review
    }

    @Test
    public void testGetOrdersByUserEmailWhenCalledThenReturnOrders() {
        // Arrange
        Order order1 = new Order();
        Order order2 = new Order();

        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findByCustomer_EmailOrderByPlacementDateDesc(any(String.class))).thenReturn(orders);

        // Act
        List<Order> returnedOrders = orderService.getOrdersByUserEmail("test@test.com");

        // Assert
        assertNotNull(returnedOrders);
        assertEquals(2, returnedOrders.size());
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
