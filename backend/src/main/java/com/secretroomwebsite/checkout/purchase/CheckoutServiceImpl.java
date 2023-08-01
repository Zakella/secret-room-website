package com.secretroomwebsite.checkout.purchase;

import com.secretroomwebsite.customer.Customer;
import com.secretroomwebsite.customer.CustomerRepository;
import com.secretroomwebsite.order.Order;
import com.secretroomwebsite.order.OrderRepository;
import com.secretroomwebsite.order.OrderService;
import com.secretroomwebsite.order.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.secretroomwebsite.order.OrderStatus.PENDING;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               OrderService orderService,
                               OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {


        // check if this is an existing customer
//        String theEmail = customer.getEmail();

        Customer customer = purchase.getCustomer();
        Optional<Customer> customerFromDB = customerRepository.findByPhone(customer.getPhone());

        if (customerFromDB.isPresent()) {
            // we found them ... let's assign them accordingly
            customer = customerFromDB.get();
            customer.setFirstName(purchase.getCustomer().getFirstName());
            customer.setLastName(purchase.getCustomer().getLastName());
        }

        customerRepository.save(customer);


        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        order.setStatus(PENDING);
        order.setShippingAddress(purchase.getShippingAddress());
        order.setShippingCost(order.getShippingOption().getCost());

        // populate order with orderItems
        List<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> {
            orderService.addItems(order, item);
            item.setOrder(order);
                }
        );

        order.setCustomer(customer);
        orderRepository.save(order);
        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

        // generate a random UUID number (UUID version-4)
        return UUID.randomUUID().toString();
    }
}









