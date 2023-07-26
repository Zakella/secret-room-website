package com.secretroomwebsite;

import com.secretroomwebsite.order.OrderStatus;
import com.secretroomwebsite.order.dto.OrderItemDTO;
import com.secretroomwebsite.order.dto.OrderRequestDTO;
import com.secretroomwebsite.product.Product;
import com.secretroomwebsite.product.category.ProductCategory;
import com.secretroomwebsite.product.category.ProductCategoryRepository;
import com.secretroomwebsite.product.dao.ProductRepository;
import com.secretroomwebsite.product.sizes.SizeType;
import com.secretroomwebsite.shipping.Shipping;
import com.secretroomwebsite.shipping.ShippingRepository;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.secretroomwebsite.TestData.*;
import static com.secretroomwebsite.enums.Brands.VictoriasSecret;


public class TestDataProvider {

    private final ShippingRepository shippingRepository;


    private final ProductRepository productRepository;


    private final ProductCategoryRepository productCategoryRepository;

    public TestDataProvider(ShippingRepository shippingRepository, ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.shippingRepository = shippingRepository;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public OrderRequestDTO prepareOrderDTOForTests() {

        Shipping shipping = shippingRepository.save(getTestShipping());

        ProductCategory savedCategory = productCategoryRepository.save(getTestProductCategory());
        Product product1 = getProduct1();
        product1.setProductCategory(savedCategory);

        Product product2 = getProduct2();
        product2.setProductCategory(savedCategory);

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);

        OrderRequestDTO orderRequestDTO = getTestOrderRequestDTO();

        OrderItemDTO orderItemDTO1 = getOrderItemDto1();
        orderItemDTO1.setProduct(savedProduct1);

        OrderItemDTO orderItemDTO2 = getOrderItemDto2();
        orderItemDTO2.setProduct(savedProduct2);

        orderRequestDTO.setItems(List.of(orderItemDTO1, orderItemDTO2));
        orderRequestDTO.setShippingOption(shipping);
        return orderRequestDTO;
    }
}


