package com.secretroomwebsite.order;

import com.secretroomwebsite.AbstractTestcontainers;
import com.secretroomwebsite.order.items.OrderItem;
import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product.dao.ProductRepository;
import com.secretroomwebsite.enums.SizeType;
import com.secretroomwebsite.product.category.ProductCategory;
import com.secretroomwebsite.product.category.ProductCategoryRepository;
import com.secretroomwebsite.shipping.Shipping;
import com.secretroomwebsite.shipping.ShippingRepository;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.secretroomwebsite.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.secretroomwebsite.enums.Brands.VictoriasSecret;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private OrderRepository underTest;

    @Enumerated(EnumType.STRING)
    private Order order;

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @BeforeEach
        void setUp() {
            Shipping shipping = getTestShipping();
            shippingRepository.save(shipping);

            order = getTestOrder();

            ProductCategory category = getTestProductCategory();
            productCategoryRepository.save(category);

            Product product = getProduct1();
            product.setProductCategory(category);

            Product product2 = getProduct2();
            product2.setProductCategory(category);

            Product savedProduct1 = this.productRepository.save(product);
            Product savedProduct2 = this.productRepository.save(product2);

            order.setItems(List.of(
                    new OrderItem(savedProduct1, SizeType.S, 5, 50.00, order),
                    new OrderItem(savedProduct2, null, 5, 100.00, order)
            ));

        }

    @Test
    void    itShouldSaveOrder() {
        // When

        Order savedOrder = underTest.save(order);

        // Then
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isEqualTo(order.getId());
    }

    @Test
    void itShouldFindOrderById() {

        Order savedOrder = underTest.save(order);

        // Given
        Long orderId = savedOrder.getId();

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
        Order savedOrder = underTest.save(order);
        Long orderId = savedOrder.getId();

        ProductCategory category = getTestProductCategory();
        productCategoryRepository.save(category);

        Product newProduct = getProduct1();
        newProduct.setProductCategory(category);

        this.productRepository.save(newProduct);
        OrderItem newOrderItem = new OrderItem(newProduct, SizeType.S, 5, 1500.00, savedOrder);

        // When
        List<OrderItem> items = new ArrayList<>(savedOrder.getItems());
        items.add(newOrderItem);
        savedOrder.setItems(items);
        underTest.save(savedOrder);

        // Fetch the updated order from the repository again
        // Fetch the updated order from the repository again
        Order updatedOrder = underTest.findById(orderId).orElse(null);
        assertNotNull(updatedOrder, "Order should not be null");

        // The newOrderItem in the updatedOrder should now have an id
        OrderItem newOrderItemInOrder = updatedOrder.getItems().stream()
                .filter(orderItem -> orderItem.getProduct().equals(newOrderItem.getProduct()))
                .findFirst()
                .orElse(null);

        assertThat(newOrderItemInOrder).isNotNull(); // to ensure that the item was found
    }

    @Test
    void itShouldDeleteOrder() {


        underTest.save(order);
        // Given
        Long orderId = order.getId();

        // When
        underTest.deleteById(orderId);

        // Then
        Optional<Order> optionalOrder = underTest.findById(orderId);
        assertThat(optionalOrder).isNotPresent();
    }

    @Test
    void itShouldValidateTotalAmountOrder() {
        // Given
        Order savedOrder = underTest.save(order);

        // When
        Double totalAmountOrder = savedOrder.getTotalAmountOrder();
        Double expectedTotalAmountOrder = savedOrder.getTotalAmount() + savedOrder.getShippingCost();

        // Then
        assertEquals(expectedTotalAmountOrder, totalAmountOrder, "TotalAmountOrder should be equal to the sum of TotalAmount and ShippingCost");
    }

    @Test
    void itShouldValidateOrderItemsNotEmpty() {
        // Given
        Order savedOrder = underTest.save(order);

        // When
        List<OrderItem> orderItems = savedOrder.getItems();

        // Then
        assertFalse(orderItems.isEmpty(), "Order items should not be empty");
    }

    @Test
    void itShouldValidateOrderItemAmount() {
        // Given
        Order savedOrder = underTest.save(order);

        // When
        List<OrderItem> orderItems = savedOrder.getItems();

        // Then
        orderItems.forEach(orderItem -> {
            Double expectedAmount = orderItem.getQuantity() * orderItem.getProduct().getUnitPrice();
            assertEquals(expectedAmount, orderItem.getAmount(), "Amount should be equal to the product of Quantity and UnitPrice for the product");
        });
    }

}