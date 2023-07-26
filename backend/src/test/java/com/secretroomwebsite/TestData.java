package com.secretroomwebsite;

import com.secretroomwebsite.order.OrderStatus;
import com.secretroomwebsite.order.dto.OrderItemDTO;
import com.secretroomwebsite.order.dto.OrderRequestDTO;
import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product.category.ProductCategory;
import com.secretroomwebsite.product.sizes.SizeType;
import com.secretroomwebsite.shipping.Shipping;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.secretroomwebsite.enums.Brands.VictoriasSecret;

public class TestData {

    public static Shipping getTestShipping() {
        Shipping shipping = new Shipping();
        shipping.setId(999L);
        shipping.setName("Posta Romana");
        shipping.setDescription("some description");
        shipping.setCost(50.0);
        shipping.setExpectedDeliveryFrom(1);
        shipping.setExpectedDeliveryTo(3);

        return shipping;
    }

    public static ProductCategory getTestProductCategory() {
        ProductCategory category = new ProductCategory();
        category.setId(999L);
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
                .id(888L)
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
                .id(777L)
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
        testOrderRequestDTO.setStatus(OrderStatus.PENDING);
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


}
