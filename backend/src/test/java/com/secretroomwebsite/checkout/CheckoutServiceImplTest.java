package com.secretroomwebsite.checkout;

import com.secretroomwebsite.customer.Customer;
import com.secretroomwebsite.customer.CustomerRepository;
import com.secretroomwebsite.emailClient.EmailService;
import com.secretroomwebsite.order.Order;
import com.secretroomwebsite.order.OrderItem;
import com.secretroomwebsite.order.OrderRepository;
import com.secretroomwebsite.order.OrderService;
import com.secretroomwebsite.purchase.Purchase;
import com.secretroomwebsite.purchase.PurchaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.TemplateEngine;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckoutServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    private Purchase purchase;

    @BeforeEach
    public void setUp() {
        purchase = new Purchase();
        purchase.setCustomer(new Customer());
        purchase.setOrder(new Order());
        purchase.setOrderItems(Collections.singletonList(new OrderItem()));
    }

    @Test
    public void testPlaceOrderWithValidPurchase() {
        // Arrange
        when(customerRepository.findByPhone(anyString())).thenReturn(Optional.empty());
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        // Act
        PurchaseResponse response = checkoutService.placeOrder(purchase);

        // Assert
        assertThat(response).isNotNull();
        verify(customerRepository).save(any(Customer.class));
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    public void testPlaceOrderWithNullPurchase() {
        // Act & Assert
        assertThatThrownBy(() -> checkoutService.placeOrder(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Purchase cannot be null");
    }

    @Test
    public void testPlaceOrderWithNullCustomerInPurchase() {
        // Arrange
        purchase.setCustomer(null);

        // Act & Assert
        assertThatThrownBy(() -> checkoutService.placeOrder(purchase))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Customer cannot be null");
    }

    @Test
    public void testPlaceOrderWithNullOrderInPurchase() {
        // Arrange
        purchase.setOrder(null);

        // Act & Assert
        assertThatThrownBy(() -> checkoutService.placeOrder(purchase))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Order cannot be null");
    }

    @Test
    public void testPlaceOrderWithNullOrEmptyOrderItemsInPurchase() {
        // Arrange
        purchase.setOrderItems(null);

        // Act & Assert
        assertThatThrownBy(() -> checkoutService.placeOrder(purchase))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Order items cannot be null or empty");
    }
}