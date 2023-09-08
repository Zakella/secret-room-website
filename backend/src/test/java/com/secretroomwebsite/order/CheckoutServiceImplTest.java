package com.secretroomwebsite.order;

import com.secretroomwebsite.checkout.CheckoutServiceImpl;
import com.secretroomwebsite.customer.Customer;
import com.secretroomwebsite.customer.CustomerRepository;
import com.secretroomwebsite.emailClient.EmailService;
import com.secretroomwebsite.purchase.Purchase;
import com.secretroomwebsite.purchase.PurchaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Collections;
import java.util.Optional;

import static com.secretroomwebsite.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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
    private EmailService emailService;
    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    private Purchase purchase;

    @BeforeEach
    public void setUp() {
        purchase = getTestPurchase();
    }

    // ... existing tests ...

    @Test
    public void testUpdateCustomerWhenCustomerExistsThenReturnUpdatedCustomer() {
        // Подготовка
        Customer existingCustomer = getTestCustomer();

        when(customerRepository.findByPhone(existingCustomer.getPhone())).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        Customer newCustomer = new Customer();
        newCustomer.setPhone(existingCustomer.getPhone());
        newCustomer.setFirstName("Новое имя");
        newCustomer.setLastName("Новая фамилия");

        // Действие
        Customer updatedCustomer = checkoutService.updateCustomer(newCustomer);

        // Проверка
        assertThat(updatedCustomer).isEqualTo(existingCustomer);
        verify(customerRepository).save(updatedCustomer);
    }

    @Test
    public void testUpdateCustomerWhenCustomerDoesNotExistThenReturnSavedCustomer() {

        Customer existingCustomer = getTestCustomer();

        when(customerRepository.findByPhone(existingCustomer.getPhone())).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        Customer newCustomer = new Customer();
        newCustomer.setPhone(existingCustomer.getPhone());
        newCustomer.setFirstName("Новое имя");
        newCustomer.setLastName("Новая фамилия");

        // Действие
        Customer updatedCustomer = checkoutService.updateCustomer(newCustomer);

        // Проверка
        assertThat(updatedCustomer.getFirstName()).isEqualTo(newCustomer.getFirstName());
        assertThat(updatedCustomer.getLastName()).isEqualTo(newCustomer.getLastName());
        verify(customerRepository).save(updatedCustomer);
    }

    @Test
    public void testPlaceOrderWithNullPurchase() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> checkoutService.placeOrder(null))
                .withMessage("Purchase cannot be null");
    }

    @Test
    public void testPlaceOrderWithNullCustomerInPurchase() {
        purchase.setCustomer(null);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> checkoutService.placeOrder(purchase))
                .withMessage("Customer cannot be null");
    }

    @Test
    public void testPlaceOrderWithNullOrderInPurchase() {
        purchase.setOrder(null);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> checkoutService.placeOrder(purchase))
                .withMessage("Order cannot be null");
    }

    @Test
    public void testPlaceOrderWithNullOrEmptyOrderItemsInPurchase() {
        purchase.setOrderItems(null);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> checkoutService.placeOrder(purchase))
                .withMessage("Order items cannot be null or empty");

        purchase.setOrderItems(Collections.emptyList());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> checkoutService.placeOrder(purchase))
                .withMessage("Order items cannot be null or empty");
    }

    @Test
    public void testPlaceOrderWhenPurchaseIsNullThenThrowsIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> checkoutService.placeOrder(null))
                .withMessage("Purchase cannot be null");
    }

    @Test
    public void testPlaceOrderWhenCustomerInPurchaseIsNullThenThrowsIllegalArgumentException() {
        purchase.setCustomer(null);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> checkoutService.placeOrder(purchase))
                .withMessage("Customer cannot be null");
    }

    @Test
    public void testPlaceOrderWhenOrderInPurchaseIsNullThenThrowsIllegalArgumentException() {
        purchase.setOrder(null);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> checkoutService.placeOrder(purchase))
                .withMessage("Order cannot be null");
    }

    @Test
    public void testPlaceOrderWhenOrderItemsInPurchaseAreNullOrEmptyThenThrowsIllegalArgumentException() {
        purchase.setOrderItems(null);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> checkoutService.placeOrder(purchase))
                .withMessage("Order items cannot be null or empty");

        purchase.setOrderItems(Collections.emptyList());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> checkoutService.placeOrder(purchase))
                .withMessage("Order items cannot be null or empty");
    }

    @Test
    public void testPlaceOrderWhenPurchaseIsValidThenReturnsCorrectPurchaseResponse() {
        when(customerRepository.findByPhone(anyString())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(purchase.getCustomer());
        when(orderRepository.save(any(Order.class))).thenReturn(purchase.getOrder());


        String orderSummaryHtml = "<html><body>Order summary</body></html>";
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn(orderSummaryHtml);


        when(orderService.findOrderByTrackingNumber(anyString())).thenReturn(getTestOrderReview());

        PurchaseResponse response = checkoutService.placeOrder(purchase);

        assertThat(response.orderTrackingNumber()).isEqualTo(purchase.getOrder().getOrderTrackingNumber());
        verify(emailService).sendMessage(anyString(), anyString(), anyString());
    }
}