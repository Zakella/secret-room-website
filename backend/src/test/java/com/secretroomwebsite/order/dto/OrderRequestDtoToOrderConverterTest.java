package com.secretroomwebsite.order.dto;

import com.secretroomwebsite.TestDataProvider;
import com.secretroomwebsite.order.Order;
import com.secretroomwebsite.order.OrderStatus;
import com.secretroomwebsite.order.items.OrderItem;
import com.secretroomwebsite.shipping.Shipping;
import com.secretroomwebsite.shipping.ShippingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.secretroomwebsite.TestData.getTestOrderRequestDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class OrderRequestDtoToOrderConverterTest {

    @Mock
    private ShippingRepository shippingRepository;

    @InjectMocks
    private OrderRequestDtoToOrderConverter converter;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void convertTest() {
        // Arrange
        Shipping mockShipping = new Shipping();
        mockShipping.setCost(50.0);
        when(shippingRepository.findById(mockShipping.getId())).thenReturn(Optional.of(mockShipping));

        OrderRequestDTO testOrderRequestDTO = getTestOrderRequestDTO();

        Order order = converter.convert(testOrderRequestDTO);

        assertNotNull(order);
        assertEquals(order.getStatus(), OrderStatus.PENDING);
        assertEquals(testOrderRequestDTO.getFirstName(), order.getFirstName());
        assertEquals(testOrderRequestDTO.getLastName(), order.getLastName());
        assertEquals(testOrderRequestDTO.getEmail(), order.getEmail());
        assertEquals(testOrderRequestDTO.getPhoneNumber(), order.getPhoneNumber());
//        assertEquals(testOrderRequestDTO.getDeliveryAddress(), order.getDeliveryAddress());
        assertEquals(mockShipping, order.getShippingOption());
        assertEquals(mockShipping.getCost(), order.getShippingCost());
        assertEquals(testOrderRequestDTO.getTotalQuantity(), order.getTotalQuantity());
        assertEquals(testOrderRequestDTO.getTotalAmount(), order.getTotalAmount());
        assertEquals(testOrderRequestDTO.getTotalAmountOrder(), order.getTotalAmountOrder());

        assertEquals(testOrderRequestDTO.getItems().size(), order.getItems().size());
        for(int i = 0; i < order.getItems().size(); i++) {
            OrderItem orderItem = order.getItems().get(i);
            assertEquals(testOrderRequestDTO.getItems().get(i).getProduct(), orderItem.getProduct());
            assertEquals(testOrderRequestDTO.getItems().get(i).getSize(), orderItem.getSizeType());
            assertEquals(testOrderRequestDTO.getItems().get(i).getQuantity(), orderItem.getQuantity());
            assertEquals(testOrderRequestDTO.getItems().get(i).getAmount(), orderItem.getAmount());
            assertEquals(order, orderItem.getOrder());

        }
    }

}
