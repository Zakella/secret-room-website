package com.secretroomwebsite.order.dto;

import com.secretroomwebsite.exception.ResourceNotFoundException;
import com.secretroomwebsite.order.Order;
import com.secretroomwebsite.order.items.OrderItem;
import com.secretroomwebsite.shipping.Shipping;
import com.secretroomwebsite.shipping.ShippingRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderRequestDtoToOrderConverter {

    private final ShippingRepository shippingRepository;

    public OrderRequestDtoToOrderConverter(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    public Order convert(OrderRequestDTO orderRequestDTO) {

        System.out.println("Converting orderRequestDTO: " + orderRequestDTO);

        Shipping shipping = this.shippingRepository.findById(orderRequestDTO.getShippingOption().getId())
                .orElseThrow(() -> {
                    System.out.println("Failed to find shipping option with id: " + orderRequestDTO.getShippingOption().getId());
                    return new ResourceNotFoundException("Shipping option not found");
                });

        Order order = new Order();
        order.setFirstName(orderRequestDTO.getFirstName());
        order.setLastName(orderRequestDTO.getLastName());
        order.setEmail(orderRequestDTO.getEmail());
        order.setPhoneNumber(orderRequestDTO.getPhoneNumber());
        order.setDeliveryAddress(orderRequestDTO.getDeliveryAddress());
        order.setShippingOption(shipping);
        order.setShippingCost(shipping.getCost());
        order.setStatus(orderRequestDTO.getStatus());
        order.setTotalQuantity(orderRequestDTO.getTotalQuantity());
        order.setTotalAmount(orderRequestDTO.getTotalAmount());
        order.setTotalAmountOrder(orderRequestDTO.getTotalAmountOrder());

        List<OrderItem> orderItems = orderRequestDTO.getItems().stream()
                .map(this::convertOrderItemDtoToOrderItem)
                .collect(Collectors.toList());
        order.setItems(orderItems);

        return order;
    }

    private OrderItem convertOrderItemDtoToOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(orderItemDTO.getProduct());
        orderItem.setSizeType(orderItemDTO.getSize());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setAmount(orderItemDTO.getAmount());

        return orderItem;
    }
}
