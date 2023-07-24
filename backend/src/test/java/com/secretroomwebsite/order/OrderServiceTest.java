package com.secretroomwebsite.order;

import com.secretroomwebsite.order.dto.OrderItemDTO;
import com.secretroomwebsite.order.dto.OrderRequestDTO;
import com.secretroomwebsite.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    private OrderService underTest;

    @BeforeEach
    void setUp() {
        underTest = new OrderService(orderRepository);
    }

    @Test
    void itShouldCreateOrder() {

        Order testOrder = new Order();
        testOrder.setId(1L);
// Here you can set other properties of the Order object, if necessary

// Create test Product and OrderItemDTO objects
        Product testProduct = new Product();
        testProduct.setId(1L);
// Here you can set other properties of the Product object, if necessary

        OrderItemDTO testOrderItemDTO = new OrderItemDTO(
                testProduct,
                "M",
                2,
                50.0,
                testOrder
        );

// Create a test OrderRequestDTO object
        OrderRequestDTO testOrderRequestDTO = new OrderRequestDTO(
                "John",
                "Doe",
                "john.doe@example.com",
                "079241106",
                "123 Main St",
                1L,
                Arrays.asList(testOrderItemDTO),
                OrderStatus.PENDING,
                2,
                50.0,
                60.0
        );

// Mock the call of the save() method of the repository
        Mockito.when(orderRepository.save(ArgumentMatchers.any(Order.class))).thenReturn(testOrder);

// Call the method under test
        OrderController orderService;
        Long createdOrderId = underTest.createOrder(testOrderRequestDTO);

// Check the result
        assertEquals(testOrder.getId(), createdOrderId);

// Verify that the save() method of the repository was called exactly once
        Mockito.verify(orderRepository, Mockito.times(1)).save(ArgumentMatchers.any(Order.class));
    }

    @Test
    void itShouldGetAllOrders() {
        //given
        //when
        //then

    }
}