package com.secretroomwebsite.checkout;

import com.secretroomwebsite.customer.Customer;
import com.secretroomwebsite.customer.CustomerRepository;
import com.secretroomwebsite.emailClient.EmailService;
import com.secretroomwebsite.order.*;
import com.secretroomwebsite.purchase.Purchase;
import com.secretroomwebsite.purchase.PurchaseResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.secretroomwebsite.order.OrderStatus.PENDING;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private EmailService emailService;

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
        validatePurchase(purchase);

        Customer customer = updateCustomer(purchase.getCustomer());

        Order order = createOrder(purchase, customer);

        OrderReview orderReview = orderService.findOrderByTrackingNumber(order.getOrderTrackingNumber());
        String orderSummaryHtml = fillOrderSummaryTemplate(orderReview);

        emailService.sendMessage("zapolski.slava@gmail.com", "Order Summary", orderSummaryHtml);

        return new PurchaseResponse(order.getOrderTrackingNumber(), orderSummaryHtml);
    }



    private void validatePurchase(Purchase purchase) {
        if (purchase == null) {
            throw new IllegalArgumentException("Purchase cannot be null");
        }

        if (purchase.getCustomer() == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        if (purchase.getOrder() == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if (purchase.getOrderItems() == null || purchase.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be null or empty");
        }
    }

    private Customer updateCustomer(Customer customer) {
        Optional<Customer> customerFromDB = customerRepository.findByPhone(customer.getPhone());

        if (customerFromDB.isPresent()) {
            // we found them ... let's assign them accordingly
            customer = customerFromDB.get();
            customer.setFirstName(customer.getFirstName());
            customer.setLastName(customer.getLastName());
        }

        return customerRepository.save(customer);
    }

    private Order createOrder(Purchase purchase, Customer customer) {
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
        });

        order.setCustomer(customer);
        return orderRepository.save(order);
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number (UUID version-4)
        return UUID.randomUUID().toString();
    }


    private String fillOrderSummaryTemplate(OrderReview orderReview) {
        Context context = new Context();
        context.setVariable("customerName", orderReview.customer().getFirstName());
        context.setVariable("products", orderReview.items());
        context.setVariable("totalSum", orderReview.totalAmountOrder());
        context.setVariable("deliveryAddress", orderReview.shippingAddress());
        return templateEngine.process("order-summary", context);
    }

}









