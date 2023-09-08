package com.secretroomwebsite;

import com.secretroomwebsite.adress.Address;
import com.secretroomwebsite.order.OrderReview;
import com.secretroomwebsite.purchase.Purchase;
import com.secretroomwebsite.customer.Customer;
import com.secretroomwebsite.order.Order;
import com.secretroomwebsite.order.OrderStatus;
import com.secretroomwebsite.order.OrderItem;
import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product.category.ProductCategory;
import com.secretroomwebsite.enums.SizeType;
import com.secretroomwebsite.shipping.Shipping;

import java.time.LocalDate;
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
        category.setNameRu("Тестовая категорая");
        category.setNameRo("Category test");
        category.setDescriptionRo("Category A");
        category.setBrand(VictoriasSecret);
        category.setDescriptionRo("Category A");
        category.setImageUrl("assets/tests");
        return category;
    }



    public static Product getProduct1() {

        return Product.builder()
                .sku("SKU001")
                .productCategory(getTestProductCategory())
                .nameRu("Product 1")
                .nameRo("Tovar 1")
                .descriptionRo("Descriere 1")
                .descriptionRo("Descriere 1")
                .shortDescriptionRo("Descriere scurta 1")
                .shortDescriptionRu("Краткое описание 2")

                .descriptionRu("Description 1")
                .brand(VictoriasSecret)
                .shortDescriptionRu("Short Description 1")
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
                .nameRu("Product 2")
                .nameRo("Tovar 2")
                .descriptionRu("Description 2")
                .descriptionRo("Descriere 2")
                .shortDescriptionRu("Short Description 2")
                .shortDescriptionRo("Descriere scurta 2")
                .brand(VictoriasSecret)
                .unitPrice(20.0)
                .imageURL("image2.jpg")
                .active(true)
                .unitsInStock(200)
                .dateCreated(LocalDate.now())
                .build();

    }


    public static Purchase getTestPurchase() {
        Purchase purchase = new Purchase();
        purchase.setCustomer(getTestCustomer());
        purchase.setShippingAddress(getTestAddress());
        purchase.setOrder(getTestOrder());
        purchase.setOrderItems(getTestOrderItems());
        purchase.setLanguage("ru");
        return purchase;
    }

    public static List<OrderItem> getTestOrderItems() {
        return List.of(
                new OrderItem(getProduct1(), SizeType.S, 5, 50.00, null),
                new OrderItem(getProduct2(), null, 5, 100.00, null)
        );
    }

    public static OrderItem getTestOrderItem(Product product, SizeType sizeType, Integer quantity, Double amount) {
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
        order.setPlacementDate(new Date());
        order.setCustomer(getTestCustomer());
        // Set properties for order
        order.setShippingAddress(getTestAddress());
        order.setShippingOption(getTestShipping());
        order.setTotalQuantity(10);
        order.setTotalAmount(150.00);
        order.setShippingCost(50.00);
        order.setTotalAmountOrder(200.00);
        order.setOrderTrackingNumber(UUID.randomUUID().toString());
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
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("joe@gmail.com");
        customer.setPhone("079241107");
        return customer;
    }

    public static OrderReview getTestOrderReview() {
        Purchase purchase = getTestPurchase();
        return new OrderReview(
                "123456",
                purchase.getOrder().getPlacementDate(),
                purchase.getShippingAddress(),
                purchase.getOrder().getItems(),
                purchase.getOrder().getTotalAmount(),
                purchase.getOrder().getShippingOption(),
                purchase.getOrder().getTotalAmount(),
                purchase.getCustomer(),
                "some comment"

        );
    }


}
