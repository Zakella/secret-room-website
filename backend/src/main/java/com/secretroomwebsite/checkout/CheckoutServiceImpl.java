package com.secretroomwebsite.checkout;

import com.secretroomwebsite.adress.Address;
import com.secretroomwebsite.customer.Customer;
import com.secretroomwebsite.customer.CustomerRepository;
import com.secretroomwebsite.emailClient.EmailService;
import com.secretroomwebsite.order.*;
import com.secretroomwebsite.purchase.Purchase;
import com.secretroomwebsite.purchase.PurchaseResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static com.secretroomwebsite.order.OrderStatus.PENDING;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    private final TemplateEngine templateEngine;

    private final EmailService emailService;

    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               OrderService orderService,
                               OrderRepository orderRepository, TemplateEngine templateEngine, EmailService emailService) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.templateEngine = templateEngine;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        validatePurchase(purchase);

        Customer customer = updateCustomer(purchase.getCustomer());

        Order order = createOrder(purchase, customer);

        OrderReview orderReview = orderService.findOrderByTrackingNumber(order.getOrderTrackingNumber());
        String orderSummaryHtml = fillOrderSummaryTemplate(orderReview, purchase.getLanguage());

        emailService.sendMessage(purchase.getCustomer().getEmail(), "Order Summary", orderSummaryHtml);

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
        order.setComment(purchase.getComment());

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


    private String fillOrderSummaryTemplate(OrderReview orderReview, String language) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = formatter.format(orderReview.orderDate());

        DecimalFormat df = new DecimalFormat("#");
        String formattedSubtotal = df.format(orderReview.subtotal()) + " MDL";
        String formattedTotal = df.format(orderReview.totalAmountOrder()) + " MDL";
        String formattedShipping = df.format(orderReview.shippingOption().getCost()) + " MDL";

        Customer customer = orderReview.customer();
        Address address = orderReview.shippingAddress();

        Context context = new Context();
        context.setVariable("orderNumber", orderReview.orderNumber());
        context.setVariable("currentDate", formattedDate);
        context.setVariable("customerName", customer.getFirstName() + " " + customer.getLastName());
        context.setVariable("customerPhone", customer.getPhone());
        context.setVariable("deliveryCountry", address.getCountry());
        context.setVariable("deliveryCity", address.getCity());
        context.setVariable("deliveryStreet", address.getStreet());
        context.setVariable("deliveryZip", address.getZipCode());
        context.setVariable("orderItems", orderReview.items());
        context.setVariable("totalSum", formattedTotal);
        context.setVariable("shipping", formattedShipping);
        context.setVariable("subtotal", formattedSubtotal);
        context.setVariable("shippingOption", orderReview.shippingOption().getName());
        context.setVariable("estimatedDelivery", orderReview.shippingOption().getDescription());
        context.setVariable("comment", orderReview.comment());

        Locale locale = new Locale(language.toLowerCase());
        context.setLocale(locale);


        return templateEngine.process("order-summary", context);
    }


}









