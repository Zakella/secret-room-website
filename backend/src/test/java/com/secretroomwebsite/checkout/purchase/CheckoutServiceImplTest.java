package com.secretroomwebsite.checkout.purchase;

import com.secretroomwebsite.customer.Customer;
import com.secretroomwebsite.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.secretroomwebsite.TestData.getTestPurchase;
import static org.assertj.core.api.Assertions.as;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceImplTest {

    private Purchase purchase;
    @Mock
    private CustomerRepository customerRepository;


    CheckoutServiceImpl underTest;


    @BeforeEach
    void setUp() {
        purchase = getTestPurchase();
        underTest = new CheckoutServiceImpl(customerRepository);
    }

    @Test
    void itShouldPlaceOrder() {

        Customer customer = purchase.getCustomer();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // act
        PurchaseResponse response = underTest.placeOrder(purchase);

        // assert

        assertThat(customer).isEqualTo(purchase.getCustomer());
        assertThat(response.orderTrackingNumber()).isEqualTo(purchase.getOrder().getOrderTrackingNumber());
        assertThat(response.orderTrackingNumber()).isNotNull();
        assertThat(response.orderTrackingNumber()).isNotEmpty();
        verify(customerRepository).save(customer);

    }


}