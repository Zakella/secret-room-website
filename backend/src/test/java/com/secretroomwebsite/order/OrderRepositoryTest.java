package com.secretroomwebsite.order;

import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product.ProductRepository;
import com.secretroomwebsite.productSizes.Size;
import com.secretroomwebsite.productSizes.SizeType;
import com.secretroomwebsite.shipping.Shipping;
import com.secretroomwebsite.shipping.ShippingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.secretroomwebsite.enums.Brands.VictoriasSecret;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository underTest;

    private Order order;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ShippingRepository shippingRepository;



    @BeforeEach
    void setUp() {
        Shipping shipping = new Shipping(1L, "Test Shipping", 10.0, "Test Description", 1, 3);
        when(shippingRepository.save(any(Shipping.class))).thenReturn(shipping);

        order = new Order();
        // Set properties for order
        order.setFirstName("Test First Name");
        order.setLastName("Test Last Name");
        order.setEmail("test@example.com");
        order.setPhoneNumber("079294111");
        order.setDeliveryAddress("Test Delivery Address");
        order.setShippingOption(shipping);
        order.setTotalQuantity(1);
        order.setTotalAmount(10.0);
        order.setTotalAmountOrder(10.0);

        Product product = Product.builder()
                .id(1L)
                .sku("SKU001")
                .name("Product 1")
                .description("Description 1")
                .brand(VictoriasSecret)
                .shortDescription("Short Description 1")
                .unitPrice(10.0)
                .imageURL("image1.jpg")
                .active(true)
                .unitsInStock(100)
                .dateCreated(LocalDate.now())
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(product); // Mocking the productRepository.save() method

        Size size = new Size(product, SizeType.S, true);

        OrderItem orderItem = new OrderItem(product, size, 5, 1200.00, order);

        order.setItems(List.of(orderItem));
    }

    @Test
    void itShouldSaveOrder() {
        // When

        Order savedOrder = underTest.save(order);

        // Then
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isEqualTo(order.getId());
    }

    @Test
    void itShouldFindOrderById() {
        // Given
        Long orderId = order.getId();

        // When
        Optional<Order> optionalOrder = underTest.findById(orderId);

        // Then
        assertThat(optionalOrder)
                .isPresent()
                .hasValueSatisfying(o -> {
                    assertThat(o.getId()).isEqualTo(orderId);
                    assertThat(o.getFirstName()).isEqualTo(order.getFirstName());
                    assertThat(o.getLastName()).isEqualTo(order.getLastName());
                    // add more assertions as needed
                });
    }

    @Test
    void itShouldUpdateOrder() {
        // Given
        String updatedFirstName = "Updated First Name";
        order.setFirstName(updatedFirstName);

        // When
        Order updatedOrder = underTest.save(order);

        // Then
        assertThat(updatedOrder)
                .isNotNull()
                .matches(o -> o.getFirstName().equals(updatedFirstName));
    }

    @Test
    void itShouldDeleteOrder() {
        // Given
        Long orderId = order.getId();

        // When
        underTest.deleteById(orderId);

        // Then
        Optional<Order> optionalOrder = underTest.findById(orderId);
        assertThat(optionalOrder).isNotPresent();
    }

}