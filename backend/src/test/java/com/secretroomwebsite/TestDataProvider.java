package com.secretroomwebsite;

import com.secretroomwebsite.order.OrderStatus;
import com.secretroomwebsite.order.dto.OrderItemDTO;
import com.secretroomwebsite.order.dto.OrderRequestDTO;
import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product.sizes.SizeType;
import com.secretroomwebsite.shipping.Shipping;

import java.util.Arrays;
import java.util.Date;

public class TestDataProvider {

    public static OrderRequestDTO createTestOrderRequestDTO() {

        Product testProduct = new Product();
        testProduct.setId(1L);

        OrderItemDTO testOrderItemDTO1 = new OrderItemDTO();
        testOrderItemDTO1.setProduct(testProduct);
        testOrderItemDTO1.setSize(SizeType.M);
        testOrderItemDTO1.setQuantity(2);
        testOrderItemDTO1.setAmount(50.0);

        OrderItemDTO testOrderItemDTO2 = new OrderItemDTO();
        testOrderItemDTO2.setProduct(testProduct);
        testOrderItemDTO2.setSize(null);  // Please note that size is now of type SizeType
        testOrderItemDTO2.setQuantity(3);
        testOrderItemDTO2.setAmount(200.0);

        Shipping testShipping = new Shipping();
        testShipping.setId(1L);

        OrderRequestDTO testOrderRequestDTO = new OrderRequestDTO();
        testOrderRequestDTO.setPlacementDate(new Date());
        testOrderRequestDTO.setFirstName("John");
        testOrderRequestDTO.setLastName("Doe");
        testOrderRequestDTO.setEmail("john.doe@example.com");
        testOrderRequestDTO.setPhoneNumber("079241106");
        testOrderRequestDTO.setDeliveryAddress("123 Main St");
        testOrderRequestDTO.setItems(Arrays.asList(testOrderItemDTO1, testOrderItemDTO2));
        testOrderRequestDTO.setShippingOption(testShipping);
        testOrderRequestDTO.setDelivery(50.0);
        testOrderRequestDTO.setTotalQuantity(5);
        testOrderRequestDTO.setTotalAmount(700.0);
        testOrderRequestDTO.setTotalAmountOrder(700.0);
        testOrderRequestDTO.setStatus(OrderStatus.PENDING);

        return testOrderRequestDTO;
    }
}
