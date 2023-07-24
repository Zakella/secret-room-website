package com.secretroomwebsite.order;

import com.secretroomwebsite.order.items.OrderItem;
import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product.ProductRepository;
import com.secretroomwebsite.productSizes.Size;
import com.secretroomwebsite.productSizes.SizeType;
import com.secretroomwebsite.product_category.ProductCategory;
import com.secretroomwebsite.product_category.ProductCategoryRepository;
import com.secretroomwebsite.shipping.Shipping;
import com.secretroomwebsite.shipping.ShippingRepository;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

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
            Shipping shipping = new Shipping("Test Shipping", 10.0, "Test Description", 1, 3);
            shippingRepository.save(shipping);

            order = new Order();
            order.setStatus(OrderStatus.PENDING);
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


            ProductCategory category = new ProductCategory();
            category.setDescription("Category A");
            category.setBrand(VictoriasSecret);
            category.setCategoryName("Category A");
            category.setImageUrl("assets/tests");
            productCategoryRepository.save(category);

            Product product = Product.builder()
                    .sku("SKU001")
                    .productCategory(category)
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

            Product product2 = Product.builder()
                    .sku("SKU001")
                    .productCategory(category)
                    .name("Product 2")
                    .description("Description 2")
                    .brand(VictoriasSecret)
                    .shortDescription("Short Description 2")
                    .unitPrice(20.0)
                    .imageURL("image2.jpg")
                    .active(true)
                    .unitsInStock(200)
                    .dateCreated(LocalDate.now())
                    .build();

            this.productRepository.save(product);

            this.productRepository.save(product2);

            Size size = new Size(product, SizeType.S, true);

            OrderItem orderItem1 = new OrderItem(product, size, 5, 1200.00, order);
            OrderItem orderItem2 = new OrderItem(product2, null, 5, 1200.00, order);

            order.setItems(List.of(orderItem1, orderItem2));

//            this.orderId = order.getId();
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

        ProductCategory category = new ProductCategory();
        category.setDescription("Category B");
        category.setBrand(VictoriasSecret);
        category.setCategoryName("Category B");
        category.setImageUrl("assets/tests");
        productCategoryRepository.save(category);

        Product newProduct = Product.builder()
                .sku("SKU002")
                .productCategory(category)
                .name("Product 3")
                .description("Description 3")
                .brand(VictoriasSecret)
                .shortDescription("Short Description 3")
                .unitPrice(30.0)
                .imageURL("image3.jpg")
                .active(true)
                .unitsInStock(300)
                .dateCreated(LocalDate.now())
                .build();

        this.productRepository.save(newProduct);
        Size size = new Size(newProduct, SizeType.S, true);
        OrderItem newOrderItem = new OrderItem(newProduct, size, 5, 1500.00, savedOrder);

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

}