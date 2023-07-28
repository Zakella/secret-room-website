//package com.secretroomwebsite.checkout.purchase;
//
//import com.secretroomwebsite.customer.Customer;
//import com.secretroomwebsite.customer.CustomerRepository;
//import com.secretroomwebsite.order.Order;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static com.secretroomwebsite.TestData.*;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CheckoutServiceImplTest {
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    private CheckoutServiceImpl underTest;
//
//    private Order order;
//
//    @BeforeEach
//    void setUp() {
//
//        underTest = new CheckoutServiceImpl(customerRepository, customerService, orderService);
//
//        order = getTestOrder();
//        order.setOrderTrackingNumber(UUID.randomUUID().toString());
//
//    }
//
//    @Test
//    void testPlaceOrder() {
//        // Arrange
//        when(customerRepository.findByPhone(anyString())).thenReturn(Optional.empty());
//        when(customerRepository.save(any(Customer.class))).thenReturn(getTestCustomer());
//
//        PurchaseResponse response = underTest.placeOrder(getTestPurchase());
//
//        assertThat(response).isNotNull();
//        assertEquals(order.getOrderTrackingNumber(), response.orderTrackingNumber());
//
//        verify(customerRepository, times(1)).findByPhone(getTestCustomer().getPhone());
//        verify(customerRepository, times(1)).save(any(Customer.class));
//    }
//
//    @Test
//    void testPlaceOrderWithExistingCustomer() {
//        // Arrange
//        Purchase purchase = getTestPurchase();
//        Customer existingCustomer = getTestCustomer();
//        when(customerRepository.findByPhone("079241107")).thenReturn(Optional.of(existingCustomer));
//
//        // Act
//        PurchaseResponse response = underTest.placeOrder(purchase);
//
//        // Assert
//        assertThat(response.orderTrackingNumber()).isNotNull();
//        verify(customerRepository, times(1)).save(existingCustomer);
//        verify(customerRepository, never()).save(any(Customer.class));
//    }
//
//
//}
