package com.secretroomwebsite.checkout.purchase;
import com.secretroomwebsite.adress.Address;
import com.secretroomwebsite.customer.Customer;
import com.secretroomwebsite.order.Order;
import com.secretroomwebsite.order.items.OrderItem;
import lombok.Data;
import java.util.List;


@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Order order;
    private List<OrderItem> orderItems;

}
