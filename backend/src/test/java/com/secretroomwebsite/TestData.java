package com.secretroomwebsite;

import com.secretroomwebsite.adress.Address;
import com.secretroomwebsite.checkout.purchase.Purchase;
import com.secretroomwebsite.customer.Customer;
import com.secretroomwebsite.order.Order;
import com.secretroomwebsite.order.OrderStatus;
import com.secretroomwebsite.order.dto.OrderItemDTO;
import com.secretroomwebsite.order.dto.OrderRequestDTO;
import com.secretroomwebsite.order.items.OrderItem;
import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product.category.ProductCategory;
import com.secretroomwebsite.enums.SizeType;
import com.secretroomwebsite.shipping.Shipping;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.secretroomwebsite.enums.Brands.VictoriasSecret;

public class TestData {

    public static Shipping getTestShipping() {
        Shipping shipping = new Shipping();
        shipping.setName("Posta Romana");
        shipping.setDescription("some description");
        shipping.setCost(50.0);
        shipping.setExpectedDeliveryFrom(1);
        shipping.setExpectedDeliveryTo(3);

        return shipping;
    }

    public static ProductCategory getTestProductCategory() {
        ProductCategory category = new ProductCategory();
        category.setDescription("Category A");
        category.setBrand(VictoriasSecret);
        category.setCategoryName("Category A");
        category.setImageUrl("assets/tests");
        return category;
    }

    public static List<Product> getTestProducts() {

        return List.of(getProduct1(), getProduct2());
    }

    public static Product getProduct1() {

        return Product.builder()
                .sku("SKU001")
                .productCategory(getTestProductCategory())
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

    }


    public static Product getProduct2() {

        return Product.builder()
                .sku("SKU001")
                .productCategory(getTestProductCategory())
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

    }

    public static OrderItemDTO getOrderItemDto1() {

        OrderItemDTO testOrderItemDTO1 = new OrderItemDTO();
        testOrderItemDTO1.setProduct(getProduct1());
        testOrderItemDTO1.setSize(SizeType.M);
        testOrderItemDTO1.setQuantity(5);
        testOrderItemDTO1.setAmount(50.0);

        return testOrderItemDTO1;


    }

    public static OrderItemDTO getOrderItemDto2() {

        OrderItemDTO testOrderItemDTO2 = new OrderItemDTO();
        testOrderItemDTO2.setProduct(getProduct2());
        testOrderItemDTO2.setSize(null);  // Please note that size is now of type SizeType
        testOrderItemDTO2.setQuantity(5);
        testOrderItemDTO2.setAmount(100.0);

        return testOrderItemDTO2;


    }

    public static OrderRequestDTO getTestOrderRequestDTO() {

        OrderRequestDTO testOrderRequestDTO = new OrderRequestDTO();
        testOrderRequestDTO.setPlacementDate(new Date());
        testOrderRequestDTO.setFirstName("John");
        testOrderRequestDTO.setLastName("Doe");
        testOrderRequestDTO.setEmail("john.doe@example.com");
        testOrderRequestDTO.setPhoneNumber("079241106");
        testOrderRequestDTO.setDeliveryAddress("123 Main St");
        testOrderRequestDTO.setItems(Arrays.asList(getOrderItemDto1(), getOrderItemDto2()));
        testOrderRequestDTO.setShippingOption(getTestShipping());
        testOrderRequestDTO.setTotalQuantity(10);
        testOrderRequestDTO.setTotalAmount(150.0);
        testOrderRequestDTO.setDelivery(50.0);
        testOrderRequestDTO.setTotalAmountOrder(200.0);
        return testOrderRequestDTO;

    }

    public static Purchase getTestPurchase() {
        Purchase purchase = new Purchase();
        purchase.setCustomer(getTestCustomer());
        purchase.setShippingAddress(getTestAddress());
        purchase.setOrder(getTestOrder());
        purchase.setOrderItems(getTestOrderItems(getTestOrder()));
        return purchase;
    }

    public static List<OrderItem> getTestOrderItems(Order order) {
        return List.of(
                new OrderItem(getProduct1(), SizeType.S, 5, 50.00, order),
                new OrderItem(getProduct2(), null, 5, 100.00, order)
        );
    }

    public static OrderItem getTestOrderItem(Product product, SizeType sizeType  ,Integer quantity, Double amount) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setSizeType(sizeType);
        orderItem.setQuantity(quantity);
        orderItem.setAmount(amount);
        return orderItem;
    }

    public static Order getTestOrder() {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(getTestCustomer());
        // Set properties for order
        order.setFirstName("Test First Name");
        order.setLastName("Test Last Name");
        order.setEmail("test@example.com");
        order.setPhoneNumber("079294111");
        order.setShippingAddress(getTestAddress());
        order.setShippingOption(getTestShipping());
        order.setTotalQuantity(10);
        order.setTotalAmount(150.00);
        order.setShippingCost(50.00);
        order.setTotalAmountOrder(200.00);
        order.setOrderTrackingNumber(UUID.randomUUID().toString());
        order.setItems(getTestOrderItems(order));
        return order;

    }


    private static Address getTestAddress() {

        Address address = new Address();
        address.setCity("Bucharest");
        address.setCountry("Moldova");
        address.setStreet("Strada");
        address.setZipCode("123456");


        return address;
    }

    public static Customer getTestCustomer() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setLastName("Doe");
        customer.setEmail("joe@gmail.com@gmail.com");
        customer.setPhone("079241106");
        return customer;
    }


}
